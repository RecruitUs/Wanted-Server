package com.example.rcp1.domain.user.domain;

import com.example.rcp1.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "USER")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String phoneNumber;

    @Column
    private String specializedField;

    @Column
    private Long career;

    @Column
    private String position;

    @Column
    private String school;

    @Column
    private String job;

    @Column
    private LocalDateTime created;

    @Column
    private LocalDateTime updated;

    @Column
    private String status;


    @Builder

    public User(Long id, String email, String password, String name, String phoneNumber, String specializedField, Long career, String position, String school, String job, LocalDateTime created, LocalDateTime updated, String status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.specializedField = specializedField;
        this.career = career;
        this.position = position;
        this.school = school;
        this.job = job;
        this.created = created;
        this.updated = updated;
        this.status = status;
    }
}
