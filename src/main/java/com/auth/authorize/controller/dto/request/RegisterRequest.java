package com.auth.authorize.controller.dto.request;

public record RegisterRequest(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
