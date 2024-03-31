//package com.enigma.wmbspring.controller;
//
//import com.enigma.wmbspring.constant.APIUrl;
//import com.enigma.wmbspring.constant.ResponseMessage;
//import com.enigma.wmbspring.dto.request.NewMenuRequest;
//import com.enigma.wmbspring.dto.response.CommonResponse;
//import com.enigma.wmbspring.dto.response.MenuResponse;
//import com.enigma.wmbspring.service.MenuService;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
//@AutoConfigureMockMvc
//@ExtendWith(MockitoExtension.class)
//class MenuControllerTest {
//
//    @MockBean
//    private MenuService menuService;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    void setUp() {
//
//    }
//
//    @WithMockUser(username = "chani", roles = {"CUSTOMER"})
//    @Test
//    void shouldHave201StatuscreateNewMenu() throws Exception{
//        NewMenuRequest payload = NewMenuRequest.builder()
//                .name("satay")
//                .price(2000L)
//                .build();
//
//        MenuResponse mockResponse = MenuResponse.builder()
//                .id("menu-1")
//                .name(payload.getName())
//                .price(payload.getPrice())
//                .build();
//
//        Mockito.when(menuService.create(Mockito.any()))
//                .thenReturn(mockResponse);
//
//        MockMultipartFile mockMultipartFile = new MockMultipartFile("image","image.jpg",MediaType.MULTIPART_FORM_DATA_VALUE,)
//
//        String stringJson = objectMapper.writeValueAsString(payload);
//        mockMvc.perform(
//                MockMvcRequestBuilders.post(APIUrl.MENU_API)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
//                        .content(stringJson),
//                MockMvcRequestBuilders.multipart(false).file(mockResponse)
//        ).andExpect(MockMvcResultMatchers.status().isCreated())
//                .andDo(result -> {
//                    CommonResponse<MenuResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
//                    assertEquals(201, response.getStatusCode());
//                    assertEquals(ResponseMessage.SUCCESS_SAVE_DATA, response.getMessage());
//                    assertEquals("menu-1", response.getData().getId());
//                });
//    }
//
//    @Test
//    void getMenuById() {
//    }
//
//    @Test
//    void getAllMenu() {
//    }
//
//    @Test
//    void update() {
//    }
//
//    @Test
//    void deleteById() {
//    }
//}