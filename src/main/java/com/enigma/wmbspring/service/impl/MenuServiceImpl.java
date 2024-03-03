package com.enigma.wmbspring.service.impl;

import com.enigma.wmbspring.dto.request.NewMenuRequest;
import com.enigma.wmbspring.entity.Menu;
import com.enigma.wmbspring.repository.MenuRepository;
import com.enigma.wmbspring.service.MenuService;
import com.enigma.wmbspring.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
