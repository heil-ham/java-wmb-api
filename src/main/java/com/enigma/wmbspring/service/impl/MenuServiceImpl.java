package com.enigma.wmbspring.service.impl;

import com.enigma.wmbspring.constant.APIUrl;
import com.enigma.wmbspring.constant.ResponseMessage;
import com.enigma.wmbspring.dto.request.NewMenuRequest;
import com.enigma.wmbspring.dto.request.SearchMenuRequest;
import com.enigma.wmbspring.dto.request.UpdateMenuRequest;
import com.enigma.wmbspring.dto.response.ImageResponse;
import com.enigma.wmbspring.dto.response.MenuResponse;
import com.enigma.wmbspring.entity.Image;
import com.enigma.wmbspring.entity.Menu;
import com.enigma.wmbspring.repository.MenuRepository;
import com.enigma.wmbspring.service.ImageService;
import com.enigma.wmbspring.service.MenuService;
import com.enigma.wmbspring.specification.MenuSpecification;
import com.enigma.wmbspring.util.ValidationUtil;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final ValidationUtil validationUtil;
    private final ImageService imageService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public MenuResponse create(NewMenuRequest request) {
        validationUtil.validate(request);
        if (request.getImage().isEmpty()) throw new ConstraintViolationException("image is required",null);
        Image image = imageService.create(request.getImage());
        Menu menu = Menu.builder()
                .name(request.getName())
                .price(request.getPrice())
                .image(image)
                .build();

        menuRepository.saveAndFlush(menu);
        return convertMenuToMenuResponse(menu);
    }

    @Override
    public MenuResponse getOneById(String id) {
        Menu menu = findByIdOrThrownNotFound(id);
        return convertMenuToMenuResponse(menu);
    }

    @Transactional(readOnly = true)
    @Override
    public Menu getById(String id) {
        return findByIdOrThrownNotFound(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<MenuResponse> getAll(SearchMenuRequest request) {
        if (request.getPage() <=0) request.setPage(1);

        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of((request.getPage() - 1), request.getSize(), sort);
        Specification<Menu> specification = MenuSpecification.getSpecification(request);
        return menuRepository.findAll(specification, pageable).map(this::convertMenuToMenuResponse);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public MenuResponse update(UpdateMenuRequest request) {
        Menu currentMenu = findByIdOrThrownNotFound(request.getId());

        currentMenu.setName(request.getName());
        currentMenu.setPrice(request.getPrice());
        if (!(request.getImage() == null)) {
            Image image = imageService.create(request.getImage());
            imageService.deleteById(currentMenu.getImage().getId());
            currentMenu.setImage(image);
        }
        menuRepository.saveAndFlush(currentMenu);
        return convertMenuToMenuResponse(currentMenu);
    }

    @Override
    public Menu update(Menu menu) {
        return menuRepository.saveAndFlush(menu);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        Menu menu = getById(id);
        menuRepository.delete(menu);
    }

    private Menu findByIdOrThrownNotFound(String id) {
        return menuRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }

    private MenuResponse convertMenuToMenuResponse(Menu menu) {
        return MenuResponse.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .image(ImageResponse.builder()
                        .url(APIUrl.MENU_IMAGE_DOWNLOAD_API+menu.getImage().getId())
                        .name(menu.getImage().getName())
                        .build())
                .build();
    }
}
