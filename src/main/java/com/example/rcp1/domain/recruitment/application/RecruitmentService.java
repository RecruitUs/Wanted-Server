package com.example.rcp1.domain.recruitment.application;


import com.example.rcp1.domain.heart.domain.repository.HeartRepository;
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
    private final HeartRepository heartRepository;
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
        List<Post> posts = postRepository.findByStatusNot("D");
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
        Optional<Post> postOptional = postRepository.findByIdAndStatusNot(postId,"D");
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
            List<Post> posts = postRepository.findByUser_IdAndStatusNot(user.get().getId(),"D");
            List<PostResDTO> postDTOs = new ArrayList<PostResDTO>();
            for(Post post : posts){
                //ModelMapper.map : ENTITY -> DTO
                postDTOs.add(modelMapper.map(post, PostResDTO.class));
            }
            return postDTOs;
        }
        return null;
    }


    @Transactional
    public PostResDTO updateRecruitmentPostById(String token, PostReqDTO postReqDTO, Long postId) {
        String email = JwtUtil.getUserEmail(token, secretKey);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            //사용자를 찾을 수 없음
            return null;//null->Exception 변경 예정
        }

        User user = optionalUser.get();
        Optional<Post> optionalPost = postRepository.findByIdAndStatusNot(postId,"D");
        if (!optionalPost.isPresent()) {
            //게시물을 찾을 수 없음
            return null;
        }

        Post post = optionalPost.get();
        if (!user.getId().equals(post.getUser().getId())) {
            //올바르지 않은 사용자(해당 공고를 작성하지 않은 사용자)
            return null;
        }
        //Update
        if (postReqDTO.getTitle() != null) {
            post.setTitle(postReqDTO.getTitle());
        }

        if (postReqDTO.getCompany_photo_url() != null) {
            post.setCompany_photo_url(postReqDTO.getCompany_photo_url());
        }

        if (postReqDTO.getCompensation_recommender() > 0) {
            post.setCompensation_recommender(postReqDTO.getCompensation_recommender());
        }

        if (postReqDTO.getCompensation_applicant() > 0) {
            post.setCompensation_applicant(postReqDTO.getCompensation_applicant());
        }

        if (postReqDTO.getDueDate() != null) {
            post.setDueDate(postReqDTO.getDueDate());
        }

        if (postReqDTO.getContent() != null) {
            post.setContent(postReqDTO.getContent());
        }

        if (postReqDTO.getWorking_address() != null) {
            post.setWorking_address(postReqDTO.getWorking_address());
        }

        if (postReqDTO.getFields() != null && !postReqDTO.getFields().isEmpty()) {
            //기존 field 삭제
            for (Field field : post.getFields()) {
                fieldRepository.delete(field);
            }
            post.getFields().clear();
            //새로운 field 설정
            for(FieldDTO fieldDTO : postReqDTO.getFields()){
                Field field = new Field();
                field.setName(fieldDTO.getName());
                post.addField(field);
            }
        }
       Post updatedPost = postRepository.save(post);
       return modelMapper.map(updatedPost,PostResDTO.class);
    }

    @Transactional
    public Post deleteRecruitmentPostById(String token, Long postId) {
        String email = JwtUtil.getUserEmail(token, secretKey);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            //사용자를 찾을 수 없음
            return null;//null->Exception 변경 예정
        }

        User user = optionalUser.get();
        Optional<Post> optionalPost = postRepository.findByIdAndStatusNot(postId,"D");
        if (!optionalPost.isPresent()) {
            //게시물을 찾을 수 없음
            return null;
        }

        Post post = optionalPost.get();
        if (!user.getId().equals(post.getUser().getId())) {
            //올바르지 않은 사용자
            return null;
        }
        //Delete
        post.setStatusD();
        heartRepository.deleteByPost(post);//좋아요 테이블에서 삭제
        return postRepository.save(post);
    }
}
