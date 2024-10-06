package com.auth.authorize.controller.dto.request;

import java.util.Set;

public record RoleRequest(
        String name,
        Set<String> authorities
) {
}
