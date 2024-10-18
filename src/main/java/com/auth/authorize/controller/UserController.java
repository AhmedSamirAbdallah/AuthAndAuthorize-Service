package com.auth.authorize.controller;

import com.auth.authorize.controller.dto.request.AssignUserRequest;
import com.auth.authorize.controller.dto.response.AssignedRoleToUserResponse;
import com.auth.authorize.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/assign")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('WRITE_PRIVILEGES')")
    public ResponseEntity<AssignedRoleToUserResponse> assignRoleToUser(@RequestBody AssignUserRequest request) {
        return ResponseEntity.ok(userService.assignRoleToUser(request));
    }

}
