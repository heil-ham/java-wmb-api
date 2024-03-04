package com.enigma.wmbspring.dto.request;

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
    private Long minPrice;
    private Long maxPrice;

}
