package com.example.rcp1.domain.applying.domain;

import com.example.rcp1.domain.recruitment.domain.Post;
import com.example.rcp1.domain.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "APPLYING")
public class Applying {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    private String resume_url;

    private String recommender_email;

    @NotBlank
    private String status;

    @Builder
    public Applying(long id, User user, Post post, String resume_url, String recommender_email, String status) {
        this.id = id;
        this.user = user;
        this.post = post;
        this.resume_url = resume_url;
        this.recommender_email = recommender_email;
        this.status = status;
    }

    public void setStatusD() {
        this.status = "D";
    }
}
