package com.mooky.pet_diary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class PetDiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetDiaryApplication.class, args);
	}

}

// Done: 
// unit and integration pet test; refactor security config to separate
// private and public endpoints; JwtAuthFilter only runs on privatae endpoints;
// To do: 
// - use mapstruct library
// - refactor BlogController validation
// - Jpa projection (Blog)
// - trim String for validation

// TODO for tests : login 