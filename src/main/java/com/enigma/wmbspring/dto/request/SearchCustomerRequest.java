package com.enigma.wmbspring.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchCustomerRequest {
    private Integer page;
    private Integer size;

    private String name;
    private String phone_number;

    private String direction;
    private String sortBy;
}
