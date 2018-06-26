package com.flowers.code.api.web.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.flowers.code.api.web.view.PostPayload;

public class PostValidatorUnitTest {

    private PostValidator validator;

    @Before
    public void setup() {
	validator = new PostValidator();
    }

    @Test
    public void testPayload_IsValid() throws Exception {

	final PostPayload payload = new PostPayload();
	payload.setUserId(99L);
	payload.setTitle("This is a sample title");
	payload.setBody("This is a sample body");

	final boolean isPayloadValid = validator.isPostValid(payload);

	assertTrue(isPayloadValid);
    }

    @Test
    public void testPayload_IsNotValid() throws Exception {

	final PostPayload payload = new PostPayload();
	payload.setUserId(99L);
	payload.setTitle("This is a sample title");

	final boolean isPayloadValid = validator.isPostValid(payload);

	assertFalse(isPayloadValid);
    }
}
