package com.example.rcp1.domain.recruitment.domain;


import com.example.rcp1.domain.company.domain.Company;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="RECRUITMENT_FIELD")
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="post_id")
    @JsonIgnore //양방향 관계 무한순환참조 방지
    private Post post;

    @Column
    private String name;


    /**
     * @Entity에 @Data를 쓰면 StackOverFlow 발생확률 높음
     * @Data가 기본으로 생성해주는 toString이나 hashCode 그리고 equals에서 "모든 프로퍼티"를 사용해서 값을 구하는데,
     * 그 때 양방향 관계 때문에 무한 루프가 생길 수 있음. A->B->A->B 이런식.
     * 따라서 getter and setter를 별도 작성 **/

}
