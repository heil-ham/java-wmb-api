package com.enigma.wmbspring.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateMenuRequest {
    private String id;
    private String name;
    private Long price;
    private MultipartFile image;
}
