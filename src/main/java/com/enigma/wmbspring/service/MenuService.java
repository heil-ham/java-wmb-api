package com.enigma.wmbspring.service;

import com.enigma.wmbspring.dto.request.NewMenuRequest;
import com.enigma.wmbspring.dto.request.SearchMenuRequest;
import com.enigma.wmbspring.dto.request.UpdateMenuRequest;
import com.enigma.wmbspring.dto.response.MenuResponse;
import com.enigma.wmbspring.entity.Menu;
import org.springframework.data.domain.Page;

public interface MenuService {
    MenuResponse create(NewMenuRequest request);
    MenuResponse getOneById(String id);
    Menu getById(String id);
    Page<Menu> getAll(SearchMenuRequest request);
    MenuResponse update(UpdateMenuRequest request);
    Menu update(Menu menu);
    void delete(String id);

}
