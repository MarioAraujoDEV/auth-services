package com.authservice.auth_service.dto;

import jakarta.validation.constraints.*;

public class RegisterRequestDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Pattern(regexp = "\\S+", message = "Username cannot contain spaces")
    private String email;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username can only contain letters and numbers")
    @Pattern(regexp = "\\S+", message = "Username cannot contain spaces")
    private String username;

    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    @Pattern(regexp = "\\S+", message = "Username cannot contain spaces")
    private String name;

    @NotBlank(message = "Lastname is required")
    @Size(min = 1, max = 50, message = "Lastname must be between 1 and 50 characters")
    @Pattern(regexp = "\\S+", message = "Username cannot contain spaces")
    private String lastname;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$", message = "Password must contain at least one uppercase letter, one lowercase letter, and one number")
    @Pattern(regexp = "\\S+", message = "Username cannot contain spaces")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

