package com.example.banco.exception;

import org.springframework.http.HttpStatus;

public abstract class AbstractException extends RuntimeException {

    public AbstractException(String message) {
        super(message);
    }

    public abstract HttpStatus getStatus();

}

