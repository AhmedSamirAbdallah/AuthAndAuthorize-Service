package com.auth.authorize.controller;

import com.auth.authorize.controller.dto.request.AssignUserRequest;
import com.auth.authorize.controller.dto.response.AssignedRoleToUserResponse;
import com.auth.authorize.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/assign")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('CREATE_PRIVILEGES')")
    public ResponseEntity<AssignedRoleToUserResponse> assignRoleToUser(@RequestBody AssignUserRequest request) {
        AssignedRoleToUserResponse response = userService.assignRoleToUser(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/roles-and-authorities")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Set<String>> getUserRolesAndAuthorities(@PathVariable Long userId) {
        Set<String> rolesAndAuthorities = userService.getAllRolesAndAuthorities(userId);
        return ResponseEntity.ok(rolesAndAuthorities);
    }
}
