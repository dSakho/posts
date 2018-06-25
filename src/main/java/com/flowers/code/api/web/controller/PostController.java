package com.flowers.code.api.web.controller;

import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.flowers.code.api.service.PostService;
import com.flowers.code.api.web.view.PostPayload;
import com.flowers.code.api.web.view.PostView;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(final PostService postService) {
	this.postService = postService;
    }

    @GetMapping("/feed")
    public ResponseEntity<Collection<PostView>> getFeed() {
	return postService.getAllPosts();
    }

    @PutMapping(value = "/feed/{postId:[0-9]+}", headers = { "Accept=application/json" })
    public ResponseEntity<PostView> updatePost(@PathVariable("postId") final Long postId, @RequestBody final PostPayload payload) {
	return postService.updatePost(postId, payload);
    }

    @GetMapping("/feed/user/count")
    public ResponseEntity<Long> getUniqueUserCount() {
	return postService.getUniqueUserIdCount();
    }
}
