package com.interview.blog.service;

import com.interview.blog.dto.JwtAuthenticationResponse;
import com.interview.blog.dto.LoginRequest;
import com.interview.blog.dto.SignUpRequest;

import javax.servlet.http.HttpServletResponse;


public interface AuthenticationService {
    JwtAuthenticationResponse signIn(LoginRequest loginRequest, HttpServletResponse response);
    JwtAuthenticationResponse signUp(SignUpRequest signUpRequest, HttpServletResponse response);
}
