package com.example.security.Services;

import com.example.security.model.User;

import java.util.Optional;

public interface UserService {

    Integer save(User user);

    Optional<User> findByUsername(String username);
}
