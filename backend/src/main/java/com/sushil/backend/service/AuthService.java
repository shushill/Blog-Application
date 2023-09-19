package com.sushil.backend.service;

import com.sushil.backend.payload.LoginDto;
import com.sushil.backend.payload.RegisterDto;

public interface AuthService {

    String loginDto(LoginDto loginDto);
    String registerDto(RegisterDto registerDto);
}
