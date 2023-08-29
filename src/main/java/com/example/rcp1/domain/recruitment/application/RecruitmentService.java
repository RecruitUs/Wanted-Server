package com.example.rcp1.domain.recruitment.application;


import com.example.rcp1.domain.recruitment.domain.Field;
import com.example.rcp1.domain.recruitment.domain.Post;
import com.example.rcp1.domain.recruitment.domain.repository.PostRepository;
import com.example.rcp1.domain.recruitment.dto.FieldDTO;
import com.example.rcp1.domain.recruitment.dto.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecruitmentService {

    private final PostRepository postRepository;

    @Autowired
    public RecruitmentService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public Post createRecruitmentPost(PostDTO postDTO){
        Post post = Post.builder()
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

        for (FieldDTO fieldDTO : postDTO.getFields()){
            Field field = new Field();
            field.setTitle(field.getTitle());
            field.setPost(post);
            post.getFields().add(field);
        }
        postRepository.save(post);

        return post;
    }
}
