package com.iba.tokenchecker.fwk.utils;

import com.iba.tokenchecker.model.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    private final Environment environment;

    @Autowired
    public JwtUtil(Environment environment) {this.environment = environment;}

    public User parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(environment.getProperty("jwt.secret"))
                    .parseClaimsJws(token)
                    .getBody();

            logger.info("Token was decrypted: "+body.getSubject() + " " + body.get("role"));
            return new User(body.getSubject(), (String) body.get("role"));

        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }

    public String generateToken(User u) {
        Claims claims = Jwts.claims().setSubject(u.getName());
        claims.put("role", u.getRole());

        logger.info("Generating token for user: " + u.getName());
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, environment.getProperty("jwt.secret"))
                .compact();
    }
}