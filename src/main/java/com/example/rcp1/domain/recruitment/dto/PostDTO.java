package com.example.rcp1.domain.recruitment.dto;

import com.example.rcp1.domain.recruitment.domain.Field;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class PostDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String company_photo_url;

    @Min(0)
    private int compensation_recommender = 0;

    @Min(0)
    private int compensation_applicant = 0;

    private LocalDateTime dueDate;

    @Min(0)
    private int heart = 0;

    @NotBlank
    private String content;

    @NotBlank
    private String working_address;

    Set<FieldDTO> fields = new HashSet<>();


}
