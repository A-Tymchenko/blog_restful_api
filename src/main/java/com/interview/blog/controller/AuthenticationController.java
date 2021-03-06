package com.interview.blog.controller;

import com.interview.blog.dto.LoginRequest;
import com.interview.blog.dto.SignUpRequest;
import com.interview.blog.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authService;

    @Autowired
    public AuthenticationController(final AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody @Valid final LoginRequest loginRequest,
                                    final HttpServletResponse response) {
        return ResponseEntity.ok().body(authService.signIn(loginRequest, response));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid final SignUpRequest signUpRequest,
                                    final HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signUp(signUpRequest, response));
    }
}
