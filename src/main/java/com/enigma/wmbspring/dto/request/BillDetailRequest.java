package com.enigma.wmbspring.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillDetailRequest {
    private String menuId;
    private Integer qty;
}
