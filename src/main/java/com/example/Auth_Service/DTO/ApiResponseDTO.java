package com.example.Auth_Service.DTO;

public record ApiResponseDTO(boolean success, String message, Object data) {
    public ApiResponseDTO(boolean success, String message) {
        this(success, message, null);
    }
}