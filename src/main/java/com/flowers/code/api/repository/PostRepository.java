package com.flowers.code.api.repository;

import java.util.Collection;

import com.flowers.code.api.entity.Post;

public interface PostRepository {

    Collection<Post> findAllPosts();

    Post findPostById(final Long postId);

    Post savePost(final Post newPost);

    Post updatePost(final Long postId, final Post post);

    void deletePost(final Long postId);
}
