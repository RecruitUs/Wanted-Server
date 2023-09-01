package com.example.rcp1.domain.recruitment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class PostResDTO { //반환용 DTO
    @NotBlank
    private Long id;

    @NotBlank
    @JsonProperty("company_id")
    private Long userId;

    @NotBlank
    @JsonProperty("company_email")
    private String userEmail;

    @NotBlank
    @JsonProperty("company_name")
    private String userName;

    @NotBlank
    private String title;

    @NotBlank
    private String company_photo_url;

    @Min(0)
    private int compensation_recommender = 0;

    @Min(0)
    private int compensation_applicant = 0;

    private LocalDate dueDate;

    @NotBlank
    private String content;

    @Min(0)
    @Max(10)
    private int required_career;

    @NotBlank
    private String working_address;

    Set<FieldDTO> fields = new HashSet<>();

    @Min(0)
    private int numOfHearts;


}
