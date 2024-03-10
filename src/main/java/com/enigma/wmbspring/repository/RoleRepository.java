package com.enigma.wmbspring.repository;

import com.enigma.wmbspring.constant.UserRole;
import com.enigma.wmbspring.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRole(UserRole role);
}
