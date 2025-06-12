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

// Done: HealthEntryForm - energyLevel, activityType; add weight to profile; 
// To do: 
// - use mapstruct library
// - refactor BlogController validation
// - Jpa projection (Blog)
// - trim String for validation

// TODO for tests : login 