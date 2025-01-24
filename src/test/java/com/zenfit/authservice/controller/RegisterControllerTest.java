package com.zenfit.authservice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class RegisterControllerTest {

  /* private MockMvc mockMvc;

    @Mock
    private RegisterService registerService;

    @InjectMocks
    private RegisterController registerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializar los mocks
        mockMvc = MockMvcBuilders.standaloneSetup(registerController).setControllerAdvice(GlobalExceptionHandler.class).build();
    }

    // 1. Test de Registro Exitoso
    @Test
    void testRegisterSuccess() throws Exception {
        doNothing().when(registerService).register(any(RegisterRequestDTO.class));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"user@example.com\", \"username\": \"user123\", \"name\": \"John\", \"lastname\": \"Doe\", \"password\": \"Password123\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User registered successfully"))
                .andDo(print());
    }

    // 2. Test de Registro con Campos Faltantes
    @Test
    void testRegisterMissingEmail() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"user123\", \"name\": \"John\", \"lastname\": \"Doe\", \"password\": \"Password123\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Email is required"))
                .andDo(print());
    }

    // 3. Test de Registro con JSON Mal Formado
    @Test
    void testRegisterInvalidJson() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{email: \"invalid.com\", username: \"user123\", name: \"John\", lastname: \"Doe\", password: \"Password123\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Malformed JSON request"))
                .andDo(print());
    }

    // 4. Test de Registro con Datos Inválidos (Ejemplo: Email inválido)
    @Test
    void testRegisterInvalidEmail() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"invalid-email\", \"username\": \"user123\", \"name\": \"John\", \"lastname\": \"Doe\", \"password\": \"Password123\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Please provide a valid email address"))
                .andDo(print());
    }

    // 5. Test de Validación de Contraseña
    @Test
    void testRegisterInvalidPassword() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"user@example.com\", \"username\": \"user123\", \"name\": \"John\", \"lastname\": \"Doe\", \"password\": \"12345\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Password must contain at least one uppercase letter, one lowercase letter, and one number, Password must be at least 8 characters long"))
                .andDo(print());
    }

    @Test
    void testRegisterPasswordWithoutUppercase() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"user@example.com\", \"username\": \"user123\", \"name\": \"John\", \"lastname\": \"Doe\", \"password\": \"password123\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Password must contain at least one uppercase letter, one lowercase letter, and one number"))
                .andDo(print());
    }

    // 6. Test de Excepción General en el Servicio
    @Test
    void testRegisterGeneralException() throws Exception {
        doThrow(new RuntimeException("Unexpected error")).when(registerService).register(any(RegisterRequestDTO.class));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"user@example.com\", \"username\": \"user123\", \"name\": \"John\", \"lastname\": \"Doe\", \"password\": \"Password123\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("An error occurred: Unexpected error"))
                .andDo(print());
    }*/
}
