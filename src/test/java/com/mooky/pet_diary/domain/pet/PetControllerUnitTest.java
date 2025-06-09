package com.mooky.pet_diary.domain.pet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PetController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class) // need to exclude SecurityAutoConfiguration so that the requests come through 
public class PetControllerUnitTest {

    private @Autowired MockMvc mockMvc;
    private @MockitoBean PetService petService;
    private final String path = "/v1/pets";

    @BeforeEach
    public void setUpCurrentUser() {
        SecurityContext emptySecurityContext = SecurityContextHolder.createEmptyContext();
        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(1L);
        emptySecurityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(emptySecurityContext);
    }

    @AfterEach
    public void clearUser() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void createPet_InvalidName_ReturnMethodArgumentNotValidException() throws Exception {
        String requestBody = """
                {
                    "name": "  ",
                    "species": "cat"
                }
                """;

        this.mockMvc
                .perform(post(this.path).contentType("application/json").content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result").value("error"))
                .andExpect(jsonPath("$.errorCode").value("COM_002"));

        verify(this.petService, never()).createPet(any(), any());
    }

    @Test
    public void createPet_ReturnPetDto() throws Exception {
        String requestBody = """
                {
                    "name": "Test Pet Name",
                    "species": "dog"
                }
                """;
        this.mockMvc.perform(post(this.path).contentType("application/json").content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"));

        verify(this.petService, times(1)).createPet(any(), any());
    }

    // TODO test
    @Test
    public void updatePet_NoPetId_ReturnMethodArgumentNotValidException() throws Exception {
        String requestBody = """
                {
                    "name": "Test Pet Name",
                    "species": "dog"
                }
                """;

        this.mockMvc.perform(put(this.path + "/1").contentType("application/json").content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result").value("error"))
                .andExpect(jsonPath("$.errorCode").value("COM_002"));;
    }
}

