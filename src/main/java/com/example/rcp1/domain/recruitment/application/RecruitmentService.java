package com.example.rcp1.domain.recruitment.application;


import com.example.rcp1.domain.recruitment.domain.Field;
import com.example.rcp1.domain.recruitment.domain.Post;
import com.example.rcp1.domain.recruitment.domain.repository.FieldRepository;
import com.example.rcp1.domain.recruitment.domain.repository.PostRepository;
import com.example.rcp1.domain.recruitment.dto.FieldDTO;
import com.example.rcp1.domain.recruitment.dto.PostDTO;
import com.example.rcp1.domain.user.domain.User;
import com.example.rcp1.domain.user.domain.repository.UserRepository;
import com.example.rcp1.global.config.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RecruitmentService {


    private final PostRepository postRepository;

    @Value("${SECRET_KEY}")
    private String secretKey;
    private final UserRepository userRepository;
    private final FieldRepository fieldRepository;

    @Autowired
    public RecruitmentService(PostRepository postRepository,
                              UserRepository userRepository, FieldRepository fieldRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.fieldRepository = fieldRepository;
    }

    @Transactional
    public Post createRecruitmentPost(String token, PostDTO postDTO) {
        System.out.println("service called");
        String email = JwtUtil.getUserEmail(token, secretKey);
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            System.out.println("email searched");
            System.out.println(postDTO.toString());

            Post post = Post.builder()
                    .user(user.get())
                    .title(postDTO.getTitle())
                    .company_photo_url(postDTO.getCompany_photo_url())
                    .compensation_recommender(postDTO.getCompensation_recommender())
                    .compensation_applicant(postDTO.getCompensation_applicant())
                    .dueDate(postDTO.getDueDate())
                    .heart(postDTO.getHeart())
                    .content(postDTO.getContent())
                    .working_address(postDTO.getWorking_address())
                    .status("A")
                    .build();

            for(FieldDTO fieldDTO : postDTO.getFields()){
                Field field = new Field();
                field.setName(fieldDTO.getName());
                post.addField(field);
            }

            // Post 엔티티 저장
            postRepository.save(post);
            System.out.println("post saved. post = "+post.toString());

            return post; // Post 엔티티 반환
        }

        return null;
    }

}
