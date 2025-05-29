package com.mooky.pet_diary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PetDiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetDiaryApplication.class, args);
	}

}
// Done: refactor JwtAuthFilter to send appropriate error response; 
// To do: 
// - use mapstruct library
// - refactor BlogController validation
// - Jpa projection (Blog)
// - trim String for validation

// TODO for tests : login 