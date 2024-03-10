package com.enigma.wmbspring.service;

import com.enigma.wmbspring.entity.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Image create(MultipartFile multipartFile);
}
