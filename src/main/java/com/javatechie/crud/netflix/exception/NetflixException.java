package com.javatechie.crud.netflix.exception;

import org.springframework.http.HttpStatus;

public class NetflixException extends Exception {

    private HttpStatus code;

    public NetflixException(HttpStatus code, String message) {
        super(message);
        this.setCode(code);
    }

    public NetflixException(HttpStatus code, String message, Throwable cause) {
        super(message, cause);
        this.setCode(code);
    }

    public NetflixException(String s) {
        System.out.println("Som exception " + s);
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }
}

