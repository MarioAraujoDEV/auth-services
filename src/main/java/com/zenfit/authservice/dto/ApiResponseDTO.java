package com.zenfit.authservice.dto;

import java.util.Map;

public class ApiResponseDTO {

    private String status;
    private String message;
    private Object data;
    private Map<String, Object> error;

    public ApiResponseDTO(String message, Object data) {
        this.status = "success";
        this.message = message;
        this.data = data;
        this.error = null;
    }

    public ApiResponseDTO(String message, Map<String, Object> error) {
        this.status = "error";
        this.message = message;
        this.data = null;
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public Map<String, Object> getError() {
        return error;
    }

    public void setError(Map<String, Object> error) {
        this.error = error;
    }
}

