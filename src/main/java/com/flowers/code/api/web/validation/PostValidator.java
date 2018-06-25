package com.flowers.code.api.web.validation;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

import com.flowers.code.api.web.view.PostPayload;

public class PostValidator {

    public boolean isPostValid(final PostPayload post) {
	if (isNull(post.getUserId())) {
	    return false;
	}

	if (isEmpty(post.getTitle())) {
	    return false;
	}

	if (isEmpty(post.getBody())) {
	    return false;
	}

	return true;
    }
}
