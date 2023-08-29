package com.example.rcp1.domain.recruitment.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FieldDTO {
    @NotBlank
    private String name;
}
