package com.flowers.code.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.flowers.code.api.entity.Post;
import com.flowers.code.api.repository.PostRepository;
import com.flowers.code.api.web.validation.PostValidator;
import com.flowers.code.api.web.view.PostPayload;
import com.flowers.code.api.web.view.PostView;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceUnitTest {

    private PostService postService;

    @Mock
    private PostValidator validator;

    @Mock
    private PostRepository postRepository;

    @Before
    public void setup() {
	postService = new PostService(postRepository, validator);
    }

    @Test
    public void testGetAllPosts() throws Exception {

	when(postRepository.findAllPosts()).thenAnswer(withPostCollection());

	final ResponseEntity<Collection<PostView>> response =
		postService.getAllPosts();

	assertThat(response).isNotNull();
	assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	assertThat(response.getBody()).hasSize(2);
    }

    @Test
    public void testUpdatedPostWithPayload_Success() throws Exception {

	when(validator.isPostValid(any(PostPayload.class))).thenReturn(true);
	when(postRepository.findPostById(anyLong())).thenAnswer(withPost());
	when(postRepository.updatePost(anyLong(), any(Post.class))).thenAnswer(withUpdatedPost());

	final Long postId = 1L;
	final PostPayload payload = new PostPayload();
	payload.setUserId(99L);
	payload.setTitle("Payload Title");
	payload.setBody("Payload Body");

	final ResponseEntity<PostView> response = postService.updatePost(postId, payload);

	assertThat(response).isNotNull();
	assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	assertThat(response.getBody()).isInstanceOf(PostView.class);
	assertThat(response.getBody().getUserId()).isEqualTo(payload.getUserId());
	assertThat(response.getBody().getTitle()).isEqualTo(payload.getTitle());
	assertThat(response.getBody().getBody()).isEqualTo(payload.getBody());
    }

    @Test
    public void testUpdatePostWithPayload_FailsValidation() throws Exception {

	when(validator.isPostValid(any(PostPayload.class))).thenReturn(false);

	final Long postId = 1L;
	final PostPayload payload = new PostPayload();
	payload.setUserId(99L);
	payload.setTitle("Payload Title");
	payload.setBody("Payload Body");

	final ResponseEntity<PostView> response = postService.updatePost(postId, payload);

	assertThat(response).isNotNull();
	assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void testUpdatePostWithPayload_PostNotFound() throws Exception {

	when(validator.isPostValid(any(PostPayload.class))).thenReturn(true);
	when(postRepository.findPostById(anyLong())).thenAnswer(withNullValue());

	final Long postId = 1L;
	final PostPayload payload = new PostPayload();
	payload.setUserId(99L);
	payload.setTitle("Payload Title");
	payload.setBody("Payload Body");

	final ResponseEntity<PostView> response = postService.updatePost(postId, payload);

	assertThat(response).isNotNull();
	assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testGetUniqueUserCount() throws Exception {

	when(postRepository.findAllPosts()).thenAnswer(withPostCollection());

	final ResponseEntity<Long> response = postService.getUniqueUserIdCount();

	assertThat(response).isNotNull();
	assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	assertThat(response.getBody()).isEqualTo(1L);
    }

    private Answer<Collection<Post>> withNullValue() {
	return invocation -> {
	    return null;
	};
    }

    private Answer<Collection<Post>> withPostCollection() {
	return invocation -> {

	    final Post firstPost = new Post();
	    firstPost.setId(1L);
	    firstPost.setUserId(99L);
	    firstPost.setTitle("First Post Title");
	    firstPost.setBody("First Post Body");

	    final Post secondPost = new Post();
	    secondPost.setId(2L);
	    secondPost.setUserId(99L);
	    secondPost.setTitle("Second Post Title");
	    secondPost.setBody("Second Post Body");

	    return Arrays.asList(firstPost, secondPost);
	};
    }

    private Answer<Post> withPost() {
	return invocation -> {

	    final Post post = new Post();
	    post.setId(1L);
	    post.setUserId(99L);
	    post.setTitle("Post Title");
	    post.setBody("Post Body");

	    return post;
	};
    }

    private Answer<Post> withUpdatedPost() {
	return invocation -> {

	    final Post post = new Post();
	    post.setId(1L);
	    post.setUserId(99L);
	    post.setTitle("Payload Title");
	    post.setBody("Payload Body");

	    return post;
	};
    }

}
