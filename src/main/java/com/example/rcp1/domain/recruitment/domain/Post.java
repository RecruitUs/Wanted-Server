package com.example.rcp1.domain.recruitment.domain;


import com.example.rcp1.domain.common.BaseEntity;
import com.example.rcp1.domain.company.domain.Company;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name="RECRUITMENT_POST")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="company_id")
    private Company company;

    @Column
    private String title;

    @Column
    private String company_photo_url;

    @Column
    private int compensation_recommender;

    @Column
    private int compensation_applicant;

    @Column
    private LocalDateTime dueDate;

    @Column
    private int heart;

    @Column
    private String content;

    @Column
    private String working_address;

    @Column
    private LocalDateTime created;

    @Column
    private LocalDateTime updated;

    @Column
    private String status;

    //양방향 관계 설정 - 연관관계의 주인
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    Set<Field> fields = new HashSet<>();

    @Builder
    public Post(Long id, Company company, String title, String company_photo_url, int compensation_recommender, int compensation_applicant, LocalDateTime dueDate, int heart, String content, String working_address, LocalDateTime created, LocalDateTime updated, String status, Set<Field> fields) {
        this.id = id;
        this.company = company;
        this.title = title;
        this.company_photo_url = company_photo_url;
        this.compensation_recommender = compensation_recommender;
        this.compensation_applicant = compensation_applicant;
        this.dueDate = dueDate;
        this.heart = heart;
        this.content = content;
        this.working_address = working_address;
        this.created = created;
        this.updated = updated;
        this.status = status;
        this.fields = fields;
    }

    //논리삭제
    public void setStatusD() {
        this.status = "D";
    }
}
