package com.enigma.wmbspring.dto.response;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtClaims {
    private String userAccountId;
    private List<String> roles;
}
