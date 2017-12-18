package com.iba.tokenchecker.service;

import com.iba.tokenchecker.model.user.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User(username, "ADMIN");
    }
}
