package com.auth.authorize.service;

import com.auth.authorize.common.entity.Role;
import com.auth.authorize.common.entity.User;
import com.auth.authorize.repository.RoleRepository;
import com.auth.authorize.security.jwt.JWTService;
import com.auth.authorize.controller.dto.request.AuthenticationRequest;
import com.auth.authorize.controller.dto.request.RegisterRequest;
import com.auth.authorize.controller.dto.response.AuthenticationResponse;
import com.auth.authorize.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        Role role = roleRepository.findByName("ROLE_USER").orElseThrow();

        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .roles(Set.of(role))
                .build();

        user = userRepository.save(user);
        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }

//    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        User user = userRepository.findByEmail(request.email())
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.email()));
//
//        if (!passwordEncoder.encode(request.password()).equals(user.getPassword())) {
//            throw new BadCredentialsException("invalid emial or apssword");
//        }
//        String token = jwtService.generateToken(user);
//        return new AuthenticationResponse(token);
//
//    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.email()));

        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }
}
