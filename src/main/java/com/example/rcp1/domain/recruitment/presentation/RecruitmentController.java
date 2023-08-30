package com.example.rcp1.domain.recruitment.presentation;


import com.example.rcp1.domain.recruitment.application.RecruitmentService;
import com.example.rcp1.domain.recruitment.domain.Post;
import com.example.rcp1.domain.recruitment.dto.PostReqDTO;
import com.example.rcp1.domain.recruitment.dto.PostResDTO;
import com.example.rcp1.global.BaseResponse;
import com.example.rcp1.global.ErrorCode;
import com.example.rcp1.global.SuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/recruiting")
@RestController
@RequiredArgsConstructor
public class RecruitmentController {
    private final RecruitmentService recruitmentService;

    //채용공고 생성
    @PostMapping("/posts")
    public ResponseEntity<BaseResponse<Post>> createRecruitmentPost(@RequestHeader("Authorization") String Authorization, @Valid @RequestBody PostReqDTO postDTO) {
        try {
            String token = Authorization.substring(7);
            Post post = recruitmentService.createRecruitmentPost(token, postDTO);
            return ResponseEntity.ok(BaseResponse.success(SuccessCode.POST_CREATED_SUCCESS, post));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponse.error(ErrorCode.REQUEST_VALIDATION_EXCEPTION, "채용공고 게시에 실패했습니다."));
        }
    }

    //전체 채용공고 리스트 조회
    @GetMapping("/posts")
    public ResponseEntity<BaseResponse<List<PostResDTO>>> retrieveAllRecruitmentPostList(){
        try {
            List<PostResDTO> posts = recruitmentService.retrieveAllRecruitmentPosts();
            return ResponseEntity.ok(BaseResponse.success(SuccessCode.POST_RETRIEVAL_SUCCESS, posts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponse.error(ErrorCode.REQUEST_VALIDATION_EXCEPTION, "채용공고 조회에 실패했습니다."));
        }
    }

    //특정 채용공고 조회
    @GetMapping("/posts/{postId}")
    public ResponseEntity<BaseResponse<PostResDTO>> retrieveRecruitmentPostById(@PathVariable Long postId){
        try {
            PostResDTO post = recruitmentService.retrieveRecruitmentPostById(postId);
            if (post != null) {
                return ResponseEntity.ok(BaseResponse.success(SuccessCode.POST_RETRIEVAL_SUCCESS, post));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(BaseResponse.error(ErrorCode.NOT_FOUND, "채용공고를 찾을 수 없습니다."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponse.error(ErrorCode.REQUEST_VALIDATION_EXCEPTION, "채용공고 조회에 실패했습니다."));
        }
    }

    //현재 로그인한 기업 유저가 작성한 채용공고 리스트 조회
    @GetMapping("/posts/mine")
    public ResponseEntity<BaseResponse<List<PostResDTO>>> retrieveRecruitmentPostsOfCurrentCompany(@RequestHeader("Authorization") String Authorization){
        try {
            String token = Authorization.substring(7);
            List<PostResDTO> posts = recruitmentService.retrieveRecruitmentPostsOfCurrentCompany(token);
            return ResponseEntity.ok(BaseResponse.success(SuccessCode.POST_RETRIEVAL_SUCCESS, posts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponse.error(ErrorCode.REQUEST_VALIDATION_EXCEPTION, "채용공고 조회에 실패했습니다."));
        }
    }




}
