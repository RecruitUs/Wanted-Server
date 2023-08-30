package com.example.rcp1.domain.applying.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SetResultReq {

    @NotBlank
    private String status;
}
