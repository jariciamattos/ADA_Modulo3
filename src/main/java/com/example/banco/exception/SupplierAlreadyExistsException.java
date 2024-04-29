package com.example.banco.exception;

public class SupplierAlreadyExistsException extends RuntimeException{

    public SupplierAlreadyExistsException() {
    }

    public SupplierAlreadyExistsException(String message) {
        super(message);
    }
}
