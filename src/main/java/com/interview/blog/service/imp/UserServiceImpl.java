package com.interview.blog.service.imp;


import com.interview.blog.entity.User;
import com.interview.blog.enums.Message;
import com.interview.blog.enums.Role;
import com.interview.blog.exception.UserNotFoundException;
import com.interview.blog.repository.UserRepository;
import com.interview.blog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Optional<User> getUserByEmail(final String email) {
        if (!isUserExistByEmail(email)) {
            final String msgBody = Message.USER_NOT_FOUND.getMsgBody();
            LOG.error(msgBody);
            throw new UserNotFoundException(msgBody);
        }
        return userRepository.findByEmail(email);
    }


    @Override
    public Boolean existsUserByEmail(final String email) {
        return isUserExistByEmail(email);
    }


    @Override
    public User saveNewUser(final User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(Role.PUBLISHER));
        return userRepository.save(user);
    }

    private Boolean isUserExistByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
