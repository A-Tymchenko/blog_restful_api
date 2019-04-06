package com.interview.blog.service.imp;

import com.interview.blog.config.HeaderProperties;
import com.interview.blog.config.JwtProperties;
import com.interview.blog.dto.JwtAuthenticationResponse;
import com.interview.blog.dto.LoginRequest;
import com.interview.blog.dto.SignUpRequest;
import com.interview.blog.entity.User;
import com.interview.blog.enums.Message;
import com.interview.blog.exception.UserAlreadyExistsException;
import com.interview.blog.security.JwtProvider;
import com.interview.blog.security.UserPrincipal;
import com.interview.blog.service.AuthenticationService;
import com.interview.blog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Optional;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private final JwtProperties jwtProperties;
    private final HeaderProperties headerProperties;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @Autowired
    public AuthenticationServiceImpl(
            JwtProperties jwtProperties,
            HeaderProperties headerProperties,
            AuthenticationManager authenticationManager,
            UserService userService,
            JwtProvider jwtProvider) {
        this.jwtProperties = jwtProperties;
        this.headerProperties = headerProperties;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }


    @Transactional
    @Override
    public JwtAuthenticationResponse signIn(final LoginRequest loginRequest, final HttpServletResponse response) {
        final Optional<User> user = userService.getUserByEmail(loginRequest.getEmail());
        if (!user.isPresent()) {
            final String msgBody = Message.USER_WITH_EMAIL_NOT_EXIST.getMsgBody();
            LOG.error(msgBody);
            throw new UsernameNotFoundException(msgBody);
        }
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        final String accessToken = jwtProvider.generateAccessToken(principal);
        response.setHeader(headerProperties.getName(), headerProperties.getType() + accessToken);
        return new JwtAuthenticationResponse(accessToken, LocalDate.now());
    }

    @Transactional
    @Override
    public JwtAuthenticationResponse signUp(final SignUpRequest signUpRequest, final HttpServletResponse response) {
        if (userService.existsUserByEmail(signUpRequest.getEmail())) {
            final String msgBody = Message.USER_ALREADY_EXISTS.getMsgBody();
            LOG.error(msgBody);
            throw new UserAlreadyExistsException(msgBody);
        }
        final User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword(), signUpRequest.getPosts());
        userService.saveNewUser(user);
        final User newUser = userService.getUserByEmail(signUpRequest.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException(Message.USER_WITH_EMAIL_NOT_EXIST.getMsgBody()));
        final String accessToken = jwtProvider.generateAccessToken(UserPrincipal.create(newUser));
        response.setHeader(headerProperties.getName(), headerProperties.getType() + accessToken);
        return new JwtAuthenticationResponse(
                accessToken,
                headerProperties.getType().trim(),
                jwtProperties.getExpirationTimeMs(),
                LocalDate.now(),
                newUser.getRoles());
    }
}
