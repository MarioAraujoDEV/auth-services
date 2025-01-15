package com.authservice.auth_service.controller;

import com.authservice.auth_service.dto.LoginRequestDTO;
import com.authservice.auth_service.exception.BadCredentialsException;
import com.authservice.auth_service.exception.DatabaseConnectionException;
import com.authservice.auth_service.exception.EmptyFieldException;
import com.authservice.auth_service.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).setControllerAdvice(GlobalExceptionController.class).build();
    }

    @Test
    void testLoginSuccess() throws Exception {
        //  Test de Login con campos, esperamos un 200 OK
        String validJson = "{\"email\": \"user@example.com\", \"password\": \"validPassword123\"}";
        String mockedToken = "mocked-token"; // Simulamos un token válido
        when(loginService.login(any(LoginRequestDTO.class))).thenReturn(mockedToken);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andExpect(jsonPath("$.data").value(mockedToken))
                .andDo(print());
    }

    @Test
    void testLoginMissingFields() throws Exception {
        // Test de Login con campos faltantes, espera 400 Bad Request
        when(loginService.login(any(LoginRequestDTO.class))).thenThrow(new EmptyFieldException("Email"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\": \"password\"}"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void testLoginDatabaseError() throws Exception {
        // Test de Login con error en la base de datos, espera 500 Internal Server Error
        when(loginService.login(any(LoginRequestDTO.class))).thenThrow(new DatabaseConnectionException());

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"user@example.com\", \"password\": \"validpassword\"}"))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

    @Test
    void testLoginInvalidCredentials() throws Exception {
        // Test de Login con credenciales inválidas, espera 401 Unauthorized
        when(loginService.login(any(LoginRequestDTO.class))).thenThrow(new BadCredentialsException());

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"invalid@example.com\", \"password\": \"wrongpassword\"}"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    void testLoginInvalidJson() throws Exception {
        // Test de Login con JSON mal formado, espera 400 Bad Request
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{email: \"invalid.com\", password: \"12345\"}"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void testLoginFieldValidation() throws Exception {
        // Test de Login con campos no válidos, espera 400 Bad Request
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"invalid-email\", \"password\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void testLoginGeneralException() throws Exception {
        // Test de Login con excepción general, espera 500 Internal Server Error
        when(loginService.login(any(LoginRequestDTO.class))).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"user@example.com\", \"password\": \"password123\"}"))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

    @Test
    void testLoginNullPointerException() throws Exception {
        // Test de Login con NullPointerException, espera 500 Internal Server Error
        when(loginService.login(any(LoginRequestDTO.class))).thenThrow(new NullPointerException("NullPointerException"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"user@example.com\", \"password\": \"password123\"}"))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }
}

