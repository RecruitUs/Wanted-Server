package com.example.rcp1.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateProfileReq {

    @Email
    private String email;

    private String password;

    private String name;

    private String phoneNumber;


    private String specializedField;

    private Long career;

    private String position;

    private String school;

    private String job;


}
