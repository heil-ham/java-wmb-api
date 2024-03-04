package com.enigma.wmbspring.service;

import com.enigma.wmbspring.dto.request.NewMenuRequest;
import com.enigma.wmbspring.dto.request.SearchMenuRequest;
import com.enigma.wmbspring.entity.Menu;
import org.springframework.data.domain.Page;

public interface MenuService {
    Menu create(NewMenuRequest request);
    Menu getById(String id);
    Page<Menu> getAll(SearchMenuRequest request);

}
