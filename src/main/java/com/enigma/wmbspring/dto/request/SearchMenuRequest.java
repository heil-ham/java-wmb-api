package com.enigma.wmbspring.dto.request;

import com.enigma.wmbspring.entity.Image;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchMenuRequest {
    private Integer page;
    private Integer size;
    private String sortBy;
    private String direction;
    private String name;
    private Long price;
    private Image image;
    private Long minPrice;
    private Long maxPrice;

}
