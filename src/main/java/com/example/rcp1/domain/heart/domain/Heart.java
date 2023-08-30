package com.example.rcp1.domain.heart.domain;

import com.example.rcp1.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "HEART")
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private Company company;


    @Builder
    public Heart(Long id, User user) {
        this.user = user;
    }
}
