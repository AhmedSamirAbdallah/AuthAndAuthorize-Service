package com.auth.authorize.controller.dto.response;

import java.util.Set;

public record AssignedRoleToUserResponse(
        Long id,
        String userName,
        Set<String> roles
) {
}
