package com.enigma.wmbspring.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {
    private String id;
    private String name;
    private String phoneNumber;
    private Boolean status;
    private String userAccountId;
}
