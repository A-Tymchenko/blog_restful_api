package com.interview.blog.service;


import com.interview.blog.entity.User;
import java.util.Optional;


public interface UserService {
    Optional<User> getUserByEmail(String email);
    Boolean existsUserByEmail(String email);
    User saveNewUser(User user);
}
