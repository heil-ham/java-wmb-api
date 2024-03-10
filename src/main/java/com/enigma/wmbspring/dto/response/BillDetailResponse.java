package com.enigma.wmbspring.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillDetailResponse {
    private String id;
    private String menuId;
    private Long menuPrice;
    private Integer quantity;
}
