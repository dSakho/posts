package com.flowers.code.api;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.flowers.code.api.entity.Post;
import com.flowers.code.api.repository.PostRepository;

@Component
public class ApiFetchRunner implements ApplicationRunner {

    private static final Logger LOGGER =
	    LoggerFactory.getLogger(ApiFetchRunner.class);

    @Autowired
    private PostRepository postRepository;

    @Value("{api.url}")
    private String apiUrl;

    @Override
    public void run(final ApplicationArguments args) throws Exception {
	LOGGER.info("Initializing application with data...");

	final RestTemplate restTemplate = new RestTemplate();
	final ResponseEntity<Post[]> postList =
		restTemplate.getForEntity(apiUrl, Post[].class);

	if (postList.getBody() != null) {
	    Arrays.stream(postList.getBody()).forEach(postRepository::savePost);
	}
    }
}

