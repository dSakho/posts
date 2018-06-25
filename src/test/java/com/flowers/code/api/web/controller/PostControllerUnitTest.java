package com.flowers.code.api.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flowers.code.api.entity.Post;
import com.flowers.code.api.service.PostService;
import com.flowers.code.api.web.view.PostPayload;
import com.flowers.code.api.web.view.PostView;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PostController.class, secure = false)
public class PostControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objMapper;

    @MockBean
    private PostService postService;

    private Post[] postArray;

    @Before
    public void setup() throws JsonParseException, JsonMappingException, IOException {
	final InputStream jsonFeedStream = this.getClass().getClassLoader().getResourceAsStream("sample/posts.json");
	postArray = objMapper.readValue(jsonFeedStream, Post[].class);
    }

    @Test
    public void testGetFeed() throws Exception {

	when(postService.getAllPosts()).thenAnswer(withOkResponse(postArray));

	final MockHttpServletResponse response =
		mockMvc.perform(get("/feed"))
		.andExpect(status().isOk())
		.andDo(print())
		.andReturn()
		.getResponse();

	assertThat(response).isNotNull();
	assertThat(response.getContentAsString()).isNotNull();
    }

    @Test
    public void testUpdatedPost() throws Exception {

	final PostPayload payload = new PostPayload();
	payload.setTitle("new title");
	payload.setBody("new description");
	payload.setUserId(1L);

	final String payloadString = objMapper.writeValueAsString(payload);

	when(postService.updatePost(anyLong(), any(PostPayload.class))).thenAnswer(withUpdatedPostOkResponse(postArray[0]));

	final MockHttpServletResponse response =
		mockMvc.perform(put("/feed/1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(payloadString))
		.andExpect(status().isOk())
		.andDo(print())
		.andReturn()
		.getResponse();

	assertThat(response).isNotNull();
	assertThat(response.getContentAsString()).isNotNull();
    }

    @Test
    public void testGetPostCount() throws Exception {

	when(postService.getUniqueUserIdCount()).thenAnswer(withUniqueUserIdCount(postArray));

	final MockHttpServletResponse response =
		mockMvc.perform(get("/feed/user/count"))
		.andExpect(status().isOk())
		.andDo(print())
		.andReturn()
		.getResponse();

	assertThat(response).isNotNull();
	assertThat(response.getContentAsString()).isNotNull().isEqualTo("2");
    }

    private Answer<ResponseEntity<List<PostView>>> withOkResponse(final Post[] postArray) {
	return invocation -> {
	    return ResponseEntity.ok().body(Arrays.stream(postArray).map(PostView::new).collect(Collectors.toList()));
	};
    }

    private Answer<ResponseEntity<PostView>> withUpdatedPostOkResponse(final Post post) {
	return invocation -> {
	    return ResponseEntity.ok().body(new PostView(post));
	};
    }

    private Answer<ResponseEntity<Long>> withUniqueUserIdCount(final Post[] postArray) {
	return invocation -> {
	    return ResponseEntity.ok().body(Arrays.stream(postArray).map(Post::getUserId).distinct().count());
	};
    }
}
