package com.iba.tokenchecker.fwk.exceptionhandling;

import com.iba.tokenchecker.fwk.exception.JwtTokenMalformedException;
import com.iba.tokenchecker.fwk.exception.JwtTokenMissingException;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TokenCheckerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(TokenCheckerExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e) throws Exception {
        logger.error(e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = JwtException.class)
    public ResponseEntity<String> jwtExceptionHandler(Exception e) throws Exception{
        logger.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = JwtTokenMissingException.class)
    public ResponseEntity<String> jwtTokenMissingExceptionHandler(Exception e) throws Exception{
        logger.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = JwtTokenMalformedException.class)
    public ResponseEntity<String> jwtTokenMalformedExceptionHandler(Exception e) throws Exception{
        logger.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}