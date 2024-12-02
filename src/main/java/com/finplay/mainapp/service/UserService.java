package com.finplay.mainapp.service;

import com.finplay.mainapp.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User createUser(User user);
    User getUserById(Long userId);
    User getUserByUsername(String username);
}
