package com.flowers.code.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.flowers.code.api.repository.PostRepository;
import com.flowers.code.api.repository.PostRepositoryImpl;
import com.flowers.code.api.web.validation.PostValidator;

@Configuration
public class AppConfig {

    @Bean
    PostValidator postValidator() {
	return new PostValidator();
    }

    @Bean
    PostRepository postRepository() {
	return new PostRepositoryImpl();
    }
}
