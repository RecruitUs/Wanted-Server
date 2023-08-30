package com.example.rcp1.domain.recruitment.application;


import com.example.rcp1.domain.recruitment.domain.Field;
import com.example.rcp1.domain.recruitment.domain.Post;
import com.example.rcp1.domain.recruitment.domain.repository.FieldRepository;
import com.example.rcp1.domain.recruitment.domain.repository.PostRepository;
import com.example.rcp1.domain.recruitment.dto.FieldDTO;
import com.example.rcp1.domain.recruitment.dto.PostReqDTO;
import com.example.rcp1.domain.recruitment.dto.PostResDTO;
import com.example.rcp1.domain.user.domain.User;
import com.example.rcp1.domain.user.domain.repository.UserRepository;
import com.example.rcp1.global.config.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecruitmentService {

    @Value("${SECRET_KEY}")
    private String secretKey;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final FieldRepository fieldRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Transactional
    public Post createRecruitmentPost(String token, PostReqDTO postDTO) {
        String email = JwtUtil.getUserEmail(token, secretKey);
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
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
            return post; // Post 엔티티 반환
        }
        return null;
    }

    public List<PostResDTO> retrieveAllRecruitmentPosts(){
        List<Post> posts = postRepository.findAll();
        if(posts!=null){
            List<PostResDTO> postDTOs = new ArrayList<PostResDTO>();
            for(Post post : posts){
                //ModelMapper.map : ENTITY -> DTO
                postDTOs.add(modelMapper.map(post, PostResDTO.class));
            }
            return postDTOs;
        }
        return null;
    }

    public PostResDTO retrieveRecruitmentPostById(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        PostResDTO postResDTO = null;
        if(postOptional.isPresent()){
            Post post = postOptional.get();
            postResDTO =  modelMapper.map(post, PostResDTO.class);
        }
        return postResDTO;
    }

    public List<PostResDTO> retrieveRecruitmentPostsOfCurrentCompany(String token) {
        String email = JwtUtil.getUserEmail(token, secretKey);
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            List<Post> posts = postRepository.findByUser_Id(user.get().getId());
            List<PostResDTO> postDTOs = new ArrayList<PostResDTO>();
            for(Post post : posts){
                //ModelMapper.map : ENTITY -> DTO
                postDTOs.add(modelMapper.map(post, PostResDTO.class));
            }
            return postDTOs;
        }
        return null;
    }


}
