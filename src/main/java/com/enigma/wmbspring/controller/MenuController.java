package com.enigma.wmbspring.controller;

import com.enigma.wmbspring.constant.APIUrl;
import com.enigma.wmbspring.constant.ResponseMessage;
import com.enigma.wmbspring.dto.request.NewMenuRequest;
import com.enigma.wmbspring.dto.request.SearchMenuRequest;
import com.enigma.wmbspring.dto.response.CommonResponse;
import com.enigma.wmbspring.dto.response.MenuResponse;
import com.enigma.wmbspring.dto.response.PagingResponse;
import com.enigma.wmbspring.entity.Image;
import com.enigma.wmbspring.entity.Menu;
import com.enigma.wmbspring.service.MenuService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.MENU_API)
public class MenuController {
    private final MenuService menuService;
    private final ObjectMapper objectMapper;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<?>> createNewMenu(
            @RequestParam(name = "menu") String jsonProduct,
            @RequestParam(name = "image") MultipartFile image) {

        CommonResponse.CommonResponseBuilder<MenuResponse>responseBuilder = CommonResponse.builder();
        try {
            NewMenuRequest request = objectMapper.readValue(jsonProduct,
                    new TypeReference<NewMenuRequest>() {
                    });
            request.setImage(image);
            MenuResponse menuResponse = menuService.create(request);
            responseBuilder.statusCode(HttpStatus.CREATED.value());
            responseBuilder.message(ResponseMessage.SUCCESS_SAVE_DATA);
            responseBuilder.data(menuResponse);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBuilder.build());
        } catch (Exception e) {
            responseBuilder.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseBuilder.message(ResponseMessage.ERROR_INTERNAL_SERVER);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBuilder.build());
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Menu> getMenuById(@PathVariable String id) {
        Menu menu = menuService.getById(id);
        return ResponseEntity.ok(menu);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<MenuResponse>>> getAllMenu(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "price", required = false) Long price,
            @RequestParam(name = "image", required = false) Image image,
            @RequestParam(name = "minPrice", required = false) Long minPrice,
            @RequestParam(name = "maxPrice", required = false) Long maxPrice

    ) {
        SearchMenuRequest request = SearchMenuRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .name(name)
                .price(price)
                .image(image)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .build();

        Page<MenuResponse> menus = menuService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPage(menus.getTotalPages())
                .totalElement(menus.getTotalElements())
                .page(menus.getPageable().getPageNumber())
                .size(menus.getPageable().getPageSize())
                .hasNext(menus.hasNext())
                .hasPrevious(menus.hasPrevious())
                .build();

        CommonResponse<List<MenuResponse>> response = CommonResponse.<List<MenuResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully get all menu")
                .data(menus.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<Menu> update(@RequestBody Menu menu) {
        Menu newMenu = menuService.update(menu);
        return ResponseEntity.ok(newMenu);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        menuService.delete(id);
        return ResponseEntity.ok("ok");
    }

}
