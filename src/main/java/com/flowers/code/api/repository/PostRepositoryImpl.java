package com.flowers.code.api.repository;

import java.util.Collection;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.springframework.stereotype.Repository;

import com.flowers.code.api.entity.Post;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private NavigableMap<Long, Post> postRepository;

    public PostRepositoryImpl() {
	postRepository = new TreeMap<>( (id1, id2) -> { return id1.compareTo(id2); });
    }

    @Override
    public Collection<Post> findAllPosts() {
	return postRepository.values();
    }

    @Override
    public Post savePost(final Post newPost) {
	if (newPost.getId() == null) {
	    newPost.setId(postRepository.lastKey() + 1);
	}

	return postRepository.put(newPost.getId(), newPost);
    }

    @Override
    public Post findPostById(final Long postId) {
	return postRepository.get(postId);
    }

    @Override
    public Post updatePost(final Long postId, final Post post) {
	return postRepository.replace(postId, post);
    }

    @Override
    public void deletePost(final Long postId) {
	final Post postToDelete = postRepository.get(postId);
	postRepository.remove(postId, postToDelete);
    }
}