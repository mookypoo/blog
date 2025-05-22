package com.mooky.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

}
// Done: add auth aspect; login; security config; added jwt service; 
// To do: use mapstruct library
// - refactor BlogController validation
// - Jpa projection (Blog)
// - trim String for validation

// TODO for tests : login 