package com.enigma.wmbspring.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCustomerRequest {
    private String id;
    private String name;
    private String phoneNumber;
}
