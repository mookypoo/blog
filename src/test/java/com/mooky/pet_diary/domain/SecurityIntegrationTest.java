package com.mooky.pet_diary.domain;


public class SecurityIntegrationTest {
    
}

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @Testcontainers
// class JwtSecurityIntegrationTest {

//     @Autowired
//     private TestRestTemplate restTemplate;

//     @Autowired
//     private JwtService jwtService;

//     @Container
//     static MariaDBContainer<?> mariaDB = new MariaDBContainer<>("mariadb:10.5.5");

//     @DynamicPropertySource
//     static void configureProperties(DynamicPropertyRegistry registry) {
//         registry.add("spring.datasource.url", mariaDB::getJdbcUrl);
//         registry.add("spring.datasource.username", mariaDB::getUsername);
//         registry.add("spring.datasource.password", mariaDB::getPassword);
//         registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
//     }

//     @Test
//     @DisplayName("Should return 401 when no JWT token provided")
//     void allEndpoints_NoToken_Returns401() {
//         String[] protectedEndpoints = {
//                 "/api/pets",
//                 "/api/pet-diary",
//                 "/api/health-log",
//                 "/api/user/profile"
//         };

//         for (String endpoint : protectedEndpoints) {
//             ResponseEntity<String> response = restTemplate.postForEntity(
//                     endpoint, new HttpEntity<>("{}"), String.class);

//             assertThat(response.getStatusCode())
//                     .as("Endpoint %s should require authentication", endpoint)
//                     .isEqualTo(HttpStatus.UNAUTHORIZED);
//         }
//     }

//     @Test
//     @DisplayName("Should return 401 when invalid JWT token provided")
//     void allEndpoints_InvalidToken_Returns401() {
//         HttpHeaders headers = new HttpHeaders();
//         headers.setBearerAuth("invalid.jwt.token");
//         headers.setContentType(MediaType.APPLICATION_JSON);

//         HttpEntity<String> request = new HttpEntity<>("{}", headers);

//         ResponseEntity<String> response = restTemplate.postForEntity(
//                 "/api/pets", request, String.class);

//         assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
//     }

//     @Test
//     @DisplayName("Should accept requests with valid JWT token")
//     void allEndpoints_ValidToken_ReturnsNon401() {
//         // Create valid token
//         String validToken = jwtService.generateToken("testuser", 1L);

//         HttpHeaders headers = new HttpHeaders();
//         headers.setBearerAuth(validToken);
//         headers.setContentType(MediaType.APPLICATION_JSON);

//         String validPetJson = """
//                 {
//                     "name": "Buddy",
//                     "species": "Dog"
//                 }
//                 """;

//         HttpEntity<String> request = new HttpEntity<>(validPetJson, headers);

//         ResponseEntity<String> response = restTemplate.postForEntity(
//                 "/api/pets", request, String.class);

//         // Should NOT be 401 (could be 400 for validation, 201 for success, etc.)
//         assertThat(response.getStatusCode()).isNotEqualTo(HttpStatus.UNAUTHORIZED);
//     }
// }