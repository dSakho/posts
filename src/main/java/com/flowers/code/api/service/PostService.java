package com.flowers.code.api.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flowers.code.api.entity.Post;
import com.flowers.code.api.repository.PostRepository;
import com.flowers.code.api.web.validation.PostValidator;
import com.flowers.code.api.web.view.PostPayload;
import com.flowers.code.api.web.view.PostView;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostValidator postValidator;

    @Autowired
    public PostService(final PostRepository postRepository, final PostValidator postValidator) {
	this.postRepository = postRepository;
	this.postValidator = postValidator;
    }

    public ResponseEntity<Collection<PostView>> getAllPosts() {
	return ResponseEntity
		.ok()
		.body(postRepository.findAllPosts().stream().map(PostView::new).collect(Collectors.toList()));
    }

    public ResponseEntity<PostView> updatePost(final Long postId, final PostPayload payload) {
	if (postValidator.isPostValid(payload)) {

	    final Post postToUpdate = postRepository.findPostById(postId);
	    if (postToUpdate != null) {
		postToUpdate.setUserId(payload.getUserId());
		postToUpdate.setTitle(payload.getTitle());
		postToUpdate.setBody(payload.getBody());

		final Post updatedPost = postRepository.updatePost(postId, postToUpdate);
		final PostView postView = new PostView(updatedPost);

		return ResponseEntity
			.ok()
			.body(postView);
	    } else {
		// 404 - Not Found
		return ResponseEntity
			.notFound()
			.build();
	    }
	} else {
	    // 422 - Unprocessable Entity
	    return ResponseEntity
		    .unprocessableEntity()
		    .build();
	}
    }

    public ResponseEntity<Long> getUniqueUserIdCount() {
	return ResponseEntity
		.ok()
		.body(postRepository.findAllPosts().stream().map(Post::getUserId).distinct().count());
    }
}
