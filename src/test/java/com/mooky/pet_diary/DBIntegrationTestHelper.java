package com.mooky.pet_diary;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.mooky.pet_diary.domain.user.User;
import com.mooky.pet_diary.domain.user.User.SignUpType;
import com.mooky.pet_diary.domain.user.repository.UserRepository;

@Testcontainers
public abstract class DBIntegrationTestHelper {

    @Container
    public static MariaDBContainer<?> mariaDB = new MariaDBContainer<>(DockerImageName.parse("mariadb:10.5.5"))
            .withDatabaseName("pet_diary_test")
            .withUsername("testuser")
            .withPassword("testpassword")
            .withInitScript("schema.sql");

    @DynamicPropertySource
    public static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mariaDB::getJdbcUrl);
        registry.add("spring.datasource.username", mariaDB::getUsername);
        registry.add("spring.datasource.password", mariaDB::getPassword);

        System.out.println("Container running: " + mariaDB.isRunning());
        System.out.println("JDBC URL: " + mariaDB.getJdbcUrl());
        System.out.println("Username: " + mariaDB.getUsername());
        System.out.println("Password: " + mariaDB.getPassword());
    }
    
    public User createAndSaveTestUser(UserRepository userRepository) {
        return userRepository.save(new User.Builder()
                    .email("test@test.com")
                    .signupType(SignUpType.EMAIL)
                    .password("testpassword")
                    .username("testusername")
                    .build());
    }

}
