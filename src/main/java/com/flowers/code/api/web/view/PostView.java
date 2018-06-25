package com.flowers.code.api.web.view;

import java.io.Serializable;

import com.flowers.code.api.entity.Post;

public class PostView implements Serializable {

    private static final long serialVersionUID = 3466657227421196032L;

    private Long id;
    private Long userId;
    private String title;
    private String body;

    public PostView() { }

    public PostView(final Post post) {
	id = post.getId();
	userId = post.getUserId();
	title = post.getTitle();
	body = post.getBody();
    }

    public Long getId() {
	return id;
    }

    public Long getUserId() {
	return userId;
    }

    public String getTitle() {
	return title;
    }

    public String getBody() {
	return body;
    }
}
