package com.flowers.code.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Spy;

import com.flowers.code.api.entity.Post;
import com.flowers.code.api.repository.PostRepository;
import com.flowers.code.api.repository.PostRepositoryImpl;

public class PostRepositoryTest {

    @Spy
    private final PostRepository postRepository = new PostRepositoryImpl();

    @Test
    public void testAddNewPost() throws Exception {

	final Post postMock = mock(Post.class);

	when(postMock.getId()).thenReturn(1L);
	when(postMock.getUserId()).thenReturn(99L);
	when(postMock.getTitle()).thenReturn("title");
	when(postMock.getBody()).thenReturn("body");

	postRepository.savePost(postMock);

	assertThat(postRepository.findAllPosts()).isNotNull().hasSize(1);
    }

    @Test
    public void testUpdatePost() throws Exception {
	final Post postMock = mock(Post.class);

	when(postMock.getId()).thenReturn(1L);
	when(postMock.getUserId()).thenReturn(99L);
	when(postMock.getTitle()).thenReturn("title");
	when(postMock.getBody()).thenReturn("body");

	postRepository.savePost(postMock);

	Post postMockToUpdate = postRepository.findPostById(postMock.getId());

	when(postMock.getTitle()).thenReturn("updated title");
	when(postMock.getBody()).thenReturn("updated body");

	postMockToUpdate = postRepository.updatePost(postMockToUpdate.getId(), postMockToUpdate);

	final Post updatedPostMock = postRepository.findPostById(postMockToUpdate.getId());

	assertThat(updatedPostMock).isNotNull();
	assertThat(postMock.getId()).isEqualTo(updatedPostMock.getId());
	assertThat(updatedPostMock.getTitle()).isEqualTo("updated title");
	assertThat(updatedPostMock.getBody()).isEqualTo("updated body");
    }

    @Test
    public void testDeletePost() throws Exception {
	final Post postMock = mock(Post.class);

	when(postMock.getId()).thenReturn(1L);
	when(postMock.getUserId()).thenReturn(99L);
	when(postMock.getTitle()).thenReturn("title");
	when(postMock.getBody()).thenReturn("body");

	postRepository.savePost(postMock);

	postRepository.deletePost(postMock.getId());

	assertThat(postRepository.findPostById(postMock.getId())).isNull();
    }
}
