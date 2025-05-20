package com.mooky.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

}
// Done: BlogServiceIntegrationTest; Refactor Blog
// To do: use mapstruct library
// - move user validation to service layer?
// - refactor BlogController validation 
// - Jpa projection (Blog)
// - blog service test & integration test (when should I do integration tests)
