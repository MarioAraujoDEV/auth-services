package com.authservice.auth_service.dto;

import java.util.Map;

public class ApiResponseDTO {
    private Object message;
    private Object data;

    // Constructor, getters y setters
    public ApiResponseDTO(Object message, Object data) {
        this.message = message;
        this.data = data;
    }

    // Getters y setters
    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
