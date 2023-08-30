package com.example.rcp1.domain.recruitment.domain;


import com.example.rcp1.domain.common.BaseEntity;
import com.example.rcp1.domain.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@NoArgsConstructor
@Table(name="RECRUITMENT_POST")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

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
    private Set<Field> fields;

    @Builder
    public Post(Long id, User user, String title, String company_photo_url, int compensation_recommender, int compensation_applicant, LocalDateTime dueDate, int heart, String content, String working_address, LocalDateTime created, LocalDateTime updated, String status) {
        this.id = id;
        this.user = user;
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
        this.fields = new HashSet<Field>();
    }

    /**
     * @Entity에 @Data를 쓰면 StackOverFlow 발생확률 높음
     * @Data가 기본으로 생성해주는 toString이나 hashCode 그리고 equals에서 "모든 프로퍼티"를 사용해서 값을 구하는데,
     * 그 때 양방향 관계 때문에 무한 루프가 생길 수 있음. A->B->A->B 이런식.
     * 따라서 getter and setter를 별도 작성
     * 해당 클래스에서는 setter 생성시 오류 발생 -> getter만 사용 조치 **/
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany_photo_url() {
        return company_photo_url;
    }

    public int getCompensation_recommender() {
        return compensation_recommender;
    }

    public int getCompensation_applicant() {
        return compensation_applicant;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public int getHeart() {
        return heart;
    }

    public String getContent() {
        return content;
    }

    public String getWorking_address() {
        return working_address;
    }

    @Override
    public LocalDateTime getCreated() {
        return created;
    }

    @Override
    public LocalDateTime getUpdated() {
        return updated;
    }

    @Override
    public String getStatus() {
        return status;
    }

    public Set<Field> getFields() {
        return fields;
    }

    //논리삭제
    public void setStatusD() {
        this.status = "D";
    }

    public void addField(Field field) {
        fields.add(field);
        field.setPost(this);
    }
}
