package com.authservice.auth_service.controller;

import com.authservice.auth_service.dto.ApiResponseDTO;
import com.authservice.auth_service.exception.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionController {

    // Manejo de excepciones de validación RegisterRquestDTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        String errorMessage = String.join(", ", errorMessages);
        ApiResponseDTO response = new ApiResponseDTO(errorMessage, null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Manejo de la excepción cuando el usuario no se encuentra
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponseDTO> handleUserNotFoundException(UserNotFoundException ex) {
        ApiResponseDTO response = new ApiResponseDTO(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);  // Código 404
    }

    // Manejo de la excepción cuando el email ya está registrado
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponseDTO> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        ApiResponseDTO response = new ApiResponseDTO(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);  // Código 409
    }

    // Manejo de la excepción cuando el nombre de usuario ya está registrado
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ApiResponseDTO> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        ApiResponseDTO response = new ApiResponseDTO(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);  // Código 409
    }

    // Manejo de las excepciones de credenciales incorrectas
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponseDTO> handleBadCredentialsException(BadCredentialsException ex) {
        ApiResponseDTO response = new ApiResponseDTO(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);  // Código 401
    }

    // Manejo de excepciones de campo vacío (por ejemplo, email o password)
    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<ApiResponseDTO> handleEmptyFieldException(EmptyFieldException ex) {
        ApiResponseDTO response = new ApiResponseDTO(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Manejo de problemas de conexión a la base de datos
    @ExceptionHandler(DatabaseConnectionException.class)
    public ResponseEntity<ApiResponseDTO> handleDatabaseConnectionException(DatabaseConnectionException ex) {
        ApiResponseDTO response = new ApiResponseDTO(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);  // Código 500
    }

    // Manejo de excepciones de integridad de datos (violaciones en la base de datos)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponseDTO> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ApiResponseDTO response = new ApiResponseDTO("Data integrity violation", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);  // Código 400
    }

    // Manejo de la excepción cuando el JSON esta mal formado
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponseDTO> handleMalformedJsonException(HttpMessageNotReadableException ex) {
        ApiResponseDTO response = new ApiResponseDTO("Malformed JSON request", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);  // Código 400
    }

    // Manejo de NullPointerException
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiResponseDTO> handleNullPointerException(NullPointerException ex) {
        ApiResponseDTO response = new ApiResponseDTO("NullPointerException" , null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);  // 500 - Internal Server Error
    }

    // Manejo de excepciones generales
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO> handleGeneralException(Exception ex) {
        ApiResponseDTO response = new ApiResponseDTO("An error occurred: " + ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);  // 500 - Internal Server Error
    }
}
