package com.pss.project.service;

import com.pss.project.payload.LoginDto;
import com.pss.project.payload.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
