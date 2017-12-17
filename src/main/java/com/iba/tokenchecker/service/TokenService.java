package com.iba.tokenchecker.service;

import com.iba.tokenchecker.model.user.User;
import com.iba.tokenchecker.fwk.utils.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class TokenService {

    @Autowired
    private JwtUtil jwtUtil;

    private ConcurrentMap<String,User> authenticatedUsers = new ConcurrentHashMap<>();

    public boolean verifyToken(String token){

        User parsedUser = jwtUtil.parseToken(token);

        if (parsedUser == null) {
            throw new JwtException("JWT token is not valid");
        }

        return authenticatedUsers.containsValue(parsedUser);
    }

    public String authenticate(String username, String password) {

        if(username.equals("ADMIN") && password.equals("1234")) {

            User user = new User(username, "ADMIN");

            authenticatedUsers.put(username, user);
            return jwtUtil.generateToken(user);
        }
        return null;
    }

    public JSONObject createTokenResponse(String token) {

        JSONObject response = new JSONObject();
        response.put("access_token", token)
                .put("token_type", "bearer");

        return response;
    }
}
