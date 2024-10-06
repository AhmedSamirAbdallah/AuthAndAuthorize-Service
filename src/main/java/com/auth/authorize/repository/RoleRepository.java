package com.auth.authorize.repository;

import com.auth.authorize.common.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Boolean existsByName(String name);

    Optional<Role> findByName(String name);

}
