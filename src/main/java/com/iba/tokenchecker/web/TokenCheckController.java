package com.iba.tokenchecker.web;

import com.iba.tokenchecker.fwk.exception.JwtTokenMissingException;
import com.iba.tokenchecker.service.TokenService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        if (!tokenService.verifyToken(authToken)) {
            logger.info("Verification failed: user is not authenticated");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> login(@RequestHeader(value = "username") String username,
                                            @RequestHeader(value = "password") String password) {

        String token = tokenService.authenticate(username, password);
        if (token == null) {
            logger.info("Authentication failed: username or password is incorrect");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(tokenService.createTokenResponse(token).toString(), HttpStatus.OK);
    }
}
