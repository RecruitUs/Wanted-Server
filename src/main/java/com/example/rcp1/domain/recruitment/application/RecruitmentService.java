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
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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
    private final EntityManager entityManager;

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
                    .required_career(postDTO.getRequired_career())
                    .working_address(postDTO.getWorking_address())
                    .status("A")
                    .build();

            for(FieldDTO fieldDTO : postDTO.getFields()){
                Field field = new Field();
                field.setName(fieldDTO.getName());
                post.addField(field);
            }
            // Post 엔티티 저장 & 반환
            return postRepository.save(post);
        }
        return null;
    }

    public List<PostResDTO> retrieveAllRecruitmentPosts(){
        List<Post> posts = postRepository.findByStatusNot("D");
        List<PostResDTO> postDTOs = new ArrayList<PostResDTO>();
        if(!posts.isEmpty()) {
            for (Post post : posts) {
                //ModelMapper.map : ENTITY -> DTO
                postDTOs.add(modelMapper.map(post, PostResDTO.class));
            }
        }
        return postDTOs;
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
        //get email by user token
        String email = JwtUtil.getUserEmail(token, secretKey);
        //get user by user email
        Optional<User> user = userRepository.findByEmail(email);
        List<PostResDTO> postDTOs = new ArrayList<PostResDTO>();
        if (user.isPresent()) {
            List<Post> posts = postRepository.findByUser_IdAndStatusNot(user.get().getId(), "D");

            for (Post post : posts) {
                //ModelMapper.map : ENTITY -> DTO
                postDTOs.add(modelMapper.map(post, PostResDTO.class));
            }
        }
        return postDTOs;
    }

    public List<PostResDTO> retrieveRecruitmentPostsByFilters(Set<String> fieldNames, Integer career) {
        /**정적 필터링 로직**/
        /**모든 필터 적용 경우의 수에 따른 조건과 repository 메소드 구현해야함**/
//        List<Post> posts = new ArrayList<>();
//        if(fieldNames!=null&&career!=null) posts = postRepository.findByFilters(fieldNames,career);
//        else if(fieldNames!=null&&career==null) posts = postRepository.findByFilters(career);
//        else if(fieldNames==null&&career!=null) posts = postRepository.findByFilters(career);
//        //선택한 필터가 없을경우
//        else if(fieldNames==null&&career==null) posts = postRepository.findAll();

        /**동적 필터링 로직 - Criteria API를 사용하여 동적 쿼리 활용**/
        /**이 방식을 이용하면 JPA Repository 사용할 필요 없음**/
        /**하지만 여전히 복잡한 코드 -> 향후 queryDSL로 교체**/
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Post> query = criteriaBuilder.createQuery(Post.class);
        Root<Post> root = query.from(Post.class);
        List<Predicate> predicates = new ArrayList<>();
        if (fieldNames != null && !fieldNames.isEmpty()) {
            SetJoin<Post, Field> fields = root.joinSet("fields", JoinType.INNER);
            predicates.add(fields.get("name").in(fieldNames));
        }
        if (career != null) {
            predicates.add(criteriaBuilder.equal(root.get("required_career"), career));
        }
        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[0]));
        }
        TypedQuery<Post> typedQuery = entityManager.createQuery(query);
        List<Post> posts = typedQuery.getResultList();

        //Entity -> DTO
        List<PostResDTO> postDTOs = new ArrayList<PostResDTO>();
        if(!posts.isEmpty()){
            for(Post post : posts){
                postDTOs.add(modelMapper.map(post, PostResDTO.class));
            }
        }
        return postDTOs;
    }

    public List<PostResDTO> retrieveRecruitmentPostsByKeyword(String keyword) {
        List<Post> posts = postRepository.findByKeyword(keyword);
        List<PostResDTO> postDTOs = new ArrayList<PostResDTO>();
        if(!posts.isEmpty()) {
            for (Post post : posts) {
                postDTOs.add(modelMapper.map(post, PostResDTO.class));
            }
        }
        return postDTOs;
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
