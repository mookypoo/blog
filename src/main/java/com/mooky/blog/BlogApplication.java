package com.mooky.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
// done - TODO redo api url (blogs, users) 
// TODO check validation method
// TODO rename to DTO
// TODO instead of autowired, use constructor depedency injection
// why? - allows immutability, testability ( can pass mock objects )
// spring team recommendation, clear dependencies 

public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

}
