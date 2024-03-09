package com.enigma.wmbspring.dto.request;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillRequest {
    private String customerId;
    private String tableName;
    private String transType;
    private List<BillDetailRequest> billDetails;
}
