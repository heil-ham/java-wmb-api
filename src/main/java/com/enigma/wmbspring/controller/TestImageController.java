package com.enigma.wmbspring.controller;

import com.enigma.wmbspring.constant.APIUrl;
import com.enigma.wmbspring.entity.Image;
import com.enigma.wmbspring.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class TestImageController {
    private final ImageService imageService;

    @PostMapping(path = APIUrl.UPLOAD_API)
    public ResponseEntity<?> testUpload(@RequestPart(name = "image")MultipartFile multipartFile) {
        Image image = imageService.create(multipartFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(image);
    }
}
