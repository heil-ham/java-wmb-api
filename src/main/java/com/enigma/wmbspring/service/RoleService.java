package com.enigma.wmbspring.service;

import com.enigma.wmbspring.constant.UserRole;
import com.enigma.wmbspring.entity.Role;

public interface RoleService {
    Role getOrSave(UserRole role);
}
