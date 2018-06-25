package com.flowers.code.api.web.view;

import java.io.Serializable;

public class PostPayload implements Serializable {

    private static final long serialVersionUID = 5671151086831444935L;

    private Long userId;
    private String title;
    private String body;

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
