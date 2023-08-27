package com.example.rcp1.global;

import lombok.RequiredArgsConstructor;

public class CustomAuthenticationException extends RuntimeException {
    public CustomAuthenticationException(String message) {
        super(message);
    }

}
