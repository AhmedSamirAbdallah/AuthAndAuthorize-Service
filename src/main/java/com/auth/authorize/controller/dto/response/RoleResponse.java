package com.auth.authorize.controller.dto.response;

import java.util.Set;

public record RoleResponse(
        Long id,
        String name,
        Set<String> authorities
) {
}
