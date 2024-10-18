package com.auth.authorize.service;

import com.auth.authorize.common.entity.Authority;
import com.auth.authorize.common.entity.Role;
import com.auth.authorize.controller.dto.request.RoleRequest;
import com.auth.authorize.controller.dto.response.RoleResponse;
import com.auth.authorize.exception.BusinessException;
import com.auth.authorize.repository.AuthorityRepository;
import com.auth.authorize.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;

    private RoleResponse mapRoleToRoleResponse(Role role) {
        return new RoleResponse(
                role.getId(),
                role.getName(),
                role.getAuthorities()
                        .stream()
                        .map(Authority::getName)
                        .collect(Collectors.toSet())
        );
    }

    @Transactional
    public RoleResponse addRole(RoleRequest request) {
        if (roleRepository.existsByName(request.name())) {
            throw new BusinessException("role name already exist.", HttpStatus.BAD_REQUEST);
        }

        Set<Authority> authoritySet = new HashSet<>();

        for (String authorityName : request.authorities()) {
            if (authorityRepository.existsByName(authorityName)) {
                throw new BusinessException("authority name already exist.", HttpStatus.BAD_REQUEST);
            }

            Authority authority = Authority.builder()
                    .name(authorityName)
                    .build();
            authorityRepository.save(authority);
            authoritySet.add(authority);
        }

        Role role = Role.builder()
                .name(request.name())
                .authorities(authoritySet)
                .build();

        role = roleRepository.save(role);

        return mapRoleToRoleResponse(role);
    }

    public List<RoleResponse> getRoles() {
        return roleRepository.findAll()
                .stream()
                .map(role -> mapRoleToRoleResponse(role))
                .toList();
    }

}
