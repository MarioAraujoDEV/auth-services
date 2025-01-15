package com.authservice.auth_service.dto;

import com.authservice.auth_service.controller.GlobalExceptionController;
import com.authservice.auth_service.controller.LoginController;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginRequestDTOTest {

    private MockMvc mockMvc;

    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).setControllerAdvice(GlobalExceptionController.class).build();
    }

    @Test
    void testLoginMissingEmail() throws Exception {
        // Simulamos que falta el campo de email en el DTO
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\": \"validPassword123\"}"))
                .andExpect(status().isBadRequest())  // Esperamos un error 400 Bad Request
                .andExpect(jsonPath("$.message").value("Email is required"))
                .andDo(print());
    }

    @Test
    void testLoginInvalidEmail() throws Exception {
        // Simulamos un email no válido
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"invalid-email\", \"password\": \"validPassword123\"}"))
                .andExpect(status().isBadRequest())  // Esperamos un error 400 Bad Request
                .andExpect(jsonPath("$.message").value("It must be a valid email"))
                .andDo(print());
    }

    @Test
    void testLoginMissingPassword() throws Exception {
        // Simulamos que falta el campo de password en el DTO
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"user@example.com\"}"))
                .andExpect(status().isBadRequest())  // Esperamos un error 400 Bad Request
                .andExpect(jsonPath("$.message").value("Password is required"))
                .andDo(print());
    }

    @Test
    void testLoginEmailAndPasswordMissing() throws Exception {
        // Simulamos que ambos campos están vacíos
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ }"))
                .andExpect(status().isBadRequest())  // Esperamos un error 400 Bad Request
                .andExpect(jsonPath("$.message").value("Email is required, Password is required"))
                .andDo(print());
    }

    @Test
    void testLoginValidEmailAndMissingPassword() throws Exception {
        // Simulamos que el email es válido y falta el password
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"user@example.com\"}"))
                .andExpect(status().isBadRequest())  // Esperamos un error 400 Bad Request
                .andExpect(jsonPath("$.message").value("Password is required"))
                .andDo(print());
    }

    @Test
    void testLoginInvalidEmailAndValidPassword() throws Exception {
        // Simulamos que el email es inválido y la contraseña es válida
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"invalid-email\", \"password\": \"validPassword123\"}"))
                .andExpect(status().isBadRequest())  // Esperamos un error 400 Bad Request
                .andExpect(jsonPath("$.message").value("It must be a valid email"))
                .andDo(print());
    }

    @Test
    void testLoginInvalidJson() throws Exception {
        // Simulamos un JSON mal formado
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{email: \"invalid\", password: \"12345\"}"))  // JSON mal formado
                .andExpect(status().isBadRequest())  // Esperamos un error 400 Bad Request
                .andExpect(jsonPath("$.message").value("Malformed JSON request"))
                .andDo(print());
    }

    @Test
    void testLoginValidInput() throws Exception {
        String validJson = "{\"email\": \"user@example.com\", \"password\": \"validPassword123\"}";
        String mockedToken = "mocked-token"; // Simulamos un token válido
        when(loginService.login(any(LoginRequestDTO.class))).thenReturn(mockedToken);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validJson))
                .andExpect(status().isOk())  // Esperamos un OK (200)
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andExpect(jsonPath("$.data").value(mockedToken))
                .andDo(print());
    }
}
