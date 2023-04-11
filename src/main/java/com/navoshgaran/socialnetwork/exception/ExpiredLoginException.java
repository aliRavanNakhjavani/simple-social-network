package com.navoshgaran.socialnetwork.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ExpiredLoginException extends RuntimeException{

    public ExpiredLoginException(String message) {
        super(message);
    }
}
