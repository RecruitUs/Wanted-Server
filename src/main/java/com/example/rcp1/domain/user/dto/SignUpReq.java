package com.example.rcp1.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SignUpReq {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    private String name;

    private String phoneNumber;

    @NotBlank
    private String specializedField;

    @NotBlank
    private Long career;

    @NotBlank
    private String position;

    @NotBlank
    private String school;

    @NotBlank
    private String job;

    private LocalDateTime created;

    private LocalDateTime updated;

    private String status;



}
