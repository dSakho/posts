package com.flowers.code.api.entity;

public class Post {

    private Long id;
    private Long userId;
    private String title;
    private String body;

    public Post() { }

    public Post(final Long id, final Long userId, final String title, final String body) {
	super();
	this.id = id;
	this.userId = userId;
	this.title = title;
	this.body = body;
    }

    public Long getId() {
	return id;
    }

    public void setId(final Long id) {
	this.id = id;
    }

    public Long getUserId() {
	return userId;
    }

    public void setUserId(final Long userId) {
	this.userId = userId;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(final String title) {
	this.title = title;
    }

    public String getBody() {
	return body;
    }

    public void setBody(final String body) {
	this.body = body;
    }
}
