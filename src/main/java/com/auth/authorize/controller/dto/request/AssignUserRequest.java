package com.auth.authorize.controller.dto.request;

public record AssignUserRequest(
        Long id,
        String roleName
) {
}
