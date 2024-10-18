package com.auth.authorize.controller;

import com.auth.authorize.controller.dto.request.RoleRequest;
import com.auth.authorize.controller.dto.response.RoleResponse;
import com.auth.authorize.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('CREATE_PRIVILEGES')")
    public ResponseEntity<RoleResponse> addRole(@RequestBody RoleRequest request) {
        return ResponseEntity.ok(roleService.addRole(request));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('READ_PRIVILEGES')")
    public ResponseEntity<List<RoleResponse>> getRoles() {
        return ResponseEntity.ok(roleService.getRoles());
    }


}
