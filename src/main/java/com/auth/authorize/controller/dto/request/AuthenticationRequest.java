package com.auth.authorize.controller.dto.request;

public record AuthenticationRequest(
        String email,
        String password
) {
}
