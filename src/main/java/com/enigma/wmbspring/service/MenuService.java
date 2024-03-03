package com.enigma.wmbspring.service;

import com.enigma.wmbspring.dto.request.NewMenuRequest;
import com.enigma.wmbspring.entity.Menu;

public interface MenuService {
    Menu create(NewMenuRequest request);
}
