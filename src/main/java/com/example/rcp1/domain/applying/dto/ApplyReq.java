package com.example.rcp1.domain.applying.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ApplyReq {

    private String resume_url;

    private String recommender_email;


}
