package com.iba.tokenchecker.service;

import com.iba.tokenchecker.model.user.User;
import com.iba.tokenchecker.utils.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TokenService {

    @Autowired
    private JwtUtil jwtUtil;

    private List<String> authenticatedUsers = new ArrayList<>();

    public boolean checkToken(String token){

        User parsedUser = jwtUtil.parseToken(token);

        if (parsedUser == null) {
            throw new JwtException("JWT token is not valid");
        } else if(authenticatedUsers.contains(parsedUser.getName())) {
            return true;
        }

        return false;
    }

    public User retreiveUser() {
        return new User("","");
    }

    public String authenticate(String username, String password) {

        if(username.equals("ADMIN") && password.equals("1234")) {
            authenticatedUsers.add(username);
            return jwtUtil.generateToken(new User(username, "ADMIN"));
        }
        return null;
    }
}
