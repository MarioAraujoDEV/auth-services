package com.authservice.auth_service.dto;

public class ApiResponseDTO {
    private String message;
    private Object data;

    public ApiResponseDTO(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    // Getters y setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
