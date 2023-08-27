package com.example.rcp1.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInReq {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
