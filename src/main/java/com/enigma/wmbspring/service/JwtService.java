package com.enigma.wmbspring.service;

import com.enigma.wmbspring.dto.response.JwtClaims;
import com.enigma.wmbspring.entity.UserAccount;

public interface JwtService {
    String generateToken(UserAccount userAccount);
    boolean verifyJwtToken(String token);
    JwtClaims getClaimsByToken(String token);
}
