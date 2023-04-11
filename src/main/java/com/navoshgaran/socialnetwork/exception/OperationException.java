package com.navoshgaran.socialnetwork.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.NOT_ACCEPTABLE,
        reason = "Unexpected error"
)
public class OperationException extends RuntimeException{

    public OperationException(String message) {
        super(message);
    }
}
