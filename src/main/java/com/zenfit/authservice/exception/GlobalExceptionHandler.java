package com.zenfit.authservice.exception;

import com.zenfit.authservice.dto.ApiResponseDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            if (!errors.containsKey(fieldName)) {
                errors.put(fieldName, errorMessage);
            }
        });
        ApiResponseDTO response = new ApiResponseDTO("Validation failed", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Manejo de excepciones específicas del usuario
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponseDTO> handleUserNotFoundException(UserNotFoundException ex) {
        Map<String, Object> errorDetails = Map.of(
                "code", "USER_001",
                "details", ex.getMessage()
        );
        ApiResponseDTO response = new ApiResponseDTO("User not found", errorDetails);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);  // Código 404
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponseDTO> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        Map<String, Object> errorDetails = Map.of(
                "code", "USER_002",
                "details", ex.getMessage()
        );
        ApiResponseDTO response = new ApiResponseDTO("Email already registered", errorDetails);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);  // Código 409
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ApiResponseDTO> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        Map<String, Object> errorDetails = Map.of(
                "code", "USER_003",
                "details", ex.getMessage()
        );
        ApiResponseDTO response = new ApiResponseDTO("Username already registered", errorDetails);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);  // Código 409
    }

    // Manejo de errores de autenticación
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponseDTO> handleBadCredentialsException(BadCredentialsException ex) {
        Map<String, Object> errorDetails = Map.of(
                "code", "AUTH_001",
                "details", "Invalid email or password"
        );
        ApiResponseDTO response = new ApiResponseDTO("Login failed", errorDetails);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);  // Código 401
    }

    // Manejo de campos vacíos
    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<ApiResponseDTO> handleEmptyFieldException(EmptyFieldException ex) {
        Map<String, Object> errorDetails = Map.of(
                "code", "FIELD_001",
                "details", ex.getMessage()
        );
        ApiResponseDTO response = new ApiResponseDTO("Empty field", errorDetails);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);  // Código 400
    }

    // Manejo de errores de conexión a la base de datos
    @ExceptionHandler(DatabaseConnectionException.class)
    public ResponseEntity<ApiResponseDTO> handleDatabaseConnectionException(DatabaseConnectionException ex) {
        Map<String, Object> errorDetails = Map.of(
                "code", "DB_001",
                "details", ex.getMessage()
        );
        ApiResponseDTO response = new ApiResponseDTO("Database connection error", errorDetails);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);  // Código 500
    }

    // Manejo de violaciones de integridad de datos
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponseDTO> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Map<String, Object> errorDetails = Map.of(
                "code", "DB_002",
                "details", "Data integrity violation"
        );
        ApiResponseDTO response = new ApiResponseDTO("Data integrity violation", errorDetails);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);  // Código 400
    }

    // Manejo de JSON malformado
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponseDTO> handleMalformedJsonException(HttpMessageNotReadableException ex) {
        Map<String, Object> errorDetails = Map.of(
                "code", "JSON_001",
                "details", "Malformed JSON request"
        );
        ApiResponseDTO response = new ApiResponseDTO("Malformed JSON request", errorDetails);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);  // Código 400
    }

    // Manejo de excepciones generales de tiempo de ejecución
    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<ApiResponseDTO> handleTokenRefreshException(RuntimeException ex) {
        Map<String, Object> errorDetails = Map.of(
                "code", "TOKEN_001",
                "details", "An error occurred: " + ex.getMessage()
        );
        ApiResponseDTO response = new ApiResponseDTO("Refresh Token is expired or invalid", errorDetails);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);  // Código 500
    }

    // Manejo de excepciones generales de tiempo de ejecución
    @ExceptionHandler(TokenGenerationException.class)
    public ResponseEntity<ApiResponseDTO> handleTokenGenerationException(RuntimeException ex) {
        Map<String, Object> errorDetails = Map.of(
                "code", "TOKEN_002",
                "details", "An error occurred: " + ex.getMessage()
        );
        ApiResponseDTO response = new ApiResponseDTO("Failed to generate refresh token", errorDetails);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);  // Código 500
    }

    // Manejo de excepciones generales
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiResponseDTO> handleNullPointerException(NullPointerException ex) {
        Map<String, Object> errorDetails = Map.of(
                "code", "GENERAL_001",
                "details", "Null pointer exception"
        );
        ApiResponseDTO response = new ApiResponseDTO("Internal Server Error", errorDetails);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);  // Código 500
    }

    // Manejo de excepciones generales de tiempo de ejecución
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponseDTO> handleGeneralRuntimeException(RuntimeException ex) {
        Map<String, Object> errorDetails = Map.of(
                "code", "GENERAL_002",
                "details", "An error occurred: " + ex.getMessage()
        );
        ApiResponseDTO response = new ApiResponseDTO("Internal Server Error", errorDetails);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);  // Código 500
    }

    // Manejo de cualquier excepción no capturada previamente
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO> handleGeneralException(Exception ex) {
        Map<String, Object> errorDetails = Map.of(
                "code", "GENERAL_003",
                "details", "An error occurred: " + ex.getMessage()
        );
        ApiResponseDTO response = new ApiResponseDTO("Internal Server Error", errorDetails);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);  // Código 500
    }
}
