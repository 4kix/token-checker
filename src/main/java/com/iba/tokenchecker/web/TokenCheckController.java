package com.iba.tokenchecker.web;

import com.iba.tokenchecker.exception.JwtTokenMissingException;
import com.iba.tokenchecker.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class TokenCheckController {

    @Autowired
    private TokenService tokenService;

    private static final Logger logger = LoggerFactory.getLogger(TokenCheckController.class);

    @RequestMapping(value = "check", method = RequestMethod.POST)
    public ResponseEntity<Void> check(@RequestHeader(value = "Authorization") String header) {

        if (header == null || !header.startsWith("Bearer ")) {
            throw new JwtTokenMissingException("No JWT token found in request headers");
        }

        String authToken = header.substring(7);

        if(tokenService.checkToken(authToken)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestHeader(value = "username") String username,
                                        @RequestHeader(value = "password") String password) {

        String token = tokenService.authenticate(username, password);

        if (token != null) {
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
