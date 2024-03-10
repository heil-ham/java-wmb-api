package com.enigma.wmbspring.service.impl;

import com.enigma.wmbspring.constant.UserRole;
import com.enigma.wmbspring.entity.Role;
import com.enigma.wmbspring.repository.RoleRepository;
import com.enigma.wmbspring.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Override
    public Role getOrSave(UserRole role) {
        return roleRepository.findByRole(role)
                .orElseGet(() -> roleRepository.saveAndFlush(Role.builder().role(role).build()));
    }
}
