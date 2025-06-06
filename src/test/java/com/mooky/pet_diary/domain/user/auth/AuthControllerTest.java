package com.mooky.pet_diary.domain.user.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.mooky.pet_diary.global.security.JwtAuthFilter;
import com.mooky.pet_diary.global.security.JwtService;

@WebMvcTest(controllers = AuthController.class, includeFilters = @ComponentScan.Filter(Configuration.class))
public class AuthControllerTest {
    
    private @Autowired MockMvc mockMvc;
    private @MockitoBean AuthService authService;
    private @MockitoBean JwtAuthFilter authFilter;
    private @MockitoBean JwtService jwtService;

    @Test
    public void testRequest() throws Exception {
        this.mockMvc.perform(post("/v1/auth/cool"))
                .andExpect(status().isOk());
    }


}
