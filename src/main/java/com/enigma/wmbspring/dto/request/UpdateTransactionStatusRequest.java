package com.enigma.wmbspring.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTransactionStatusRequest {
    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("transaction_status")
    private String transactionStatus;
}
