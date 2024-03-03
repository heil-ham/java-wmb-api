package com.enigma.wmbspring.service.impl;

import com.enigma.wmbspring.dto.request.NewMenuRequest;
import com.enigma.wmbspring.entity.Menu;
import com.enigma.wmbspring.repository.MenuRepository;
import com.enigma.wmbspring.service.MenuService;
import com.enigma.wmbspring.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final ValidationUtil validationUtil;

    @Override
    public Menu create(NewMenuRequest request) {
        validationUtil.validate(request);
        Menu menu = Menu.builder()
                .name(request.getName())
                .price(request.getPrice())
                .build();
        return menuRepository.saveAndFlush(menu);
    }

    @Override
    public Menu getById(String id) {
        Optional<Menu> optionalMenu = menuRepository.findById(id);
        if (optionalMenu.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu not found");

        return optionalMenu.get();
    }
}
