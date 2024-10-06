package com.auth.authorize.controller;

import com.auth.authorize.controller.dto.request.RoleRequest;
import com.auth.authorize.controller.dto.response.RoleResponse;
import com.auth.authorize.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RoleResponse> addRole(@RequestBody RoleRequest request) {
        return ResponseEntity.ok(roleService.addRole(request));
    }


}
