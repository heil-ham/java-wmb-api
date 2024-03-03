package com.enigma.wmbspring.controller;

import com.enigma.wmbspring.constant.APIUrl;
import com.enigma.wmbspring.dto.request.NewMenuRequest;
import com.enigma.wmbspring.dto.response.CommonResponse;
import com.enigma.wmbspring.entity.Menu;
import com.enigma.wmbspring.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.MENU_API)
public class MenuController {
    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<CommonResponse<Menu>> createNewMenu(@RequestBody NewMenuRequest request) {
        Menu menu = menuService.create(request);
        CommonResponse<Menu> response = CommonResponse.<Menu>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully create new Menu")
                .data(menu)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
