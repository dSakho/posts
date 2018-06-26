package com.flowers.code.api.web.validation;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

import com.flowers.code.api.web.view.PostPayload;

public class PostValidator {

    public boolean isPostValid(final PostPayload payload) {
	if (isNull(payload.getUserId())) {
	    return false;
	}

	if (isEmpty(payload.getTitle())) {
	    return false;
	}

	if (isEmpty(payload.getBody())) {
	    return false;
	}

	return true;
    }
}
