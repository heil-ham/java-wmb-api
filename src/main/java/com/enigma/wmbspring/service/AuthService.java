package com.enigma.wmbspring.service;

import com.enigma.wmbspring.dto.request.AuthRequest;
import com.enigma.wmbspring.dto.response.LoginResponse;
import com.enigma.wmbspring.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(AuthRequest request);
    RegisterResponse registerAdmin(AuthRequest request);
    LoginResponse login(AuthRequest request);
}
