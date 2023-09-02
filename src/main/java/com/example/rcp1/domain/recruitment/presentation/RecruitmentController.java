package com.example.rcp1.domain.recruitment.presentation;


import com.example.rcp1.domain.recruitment.application.RecruitmentService;
import com.example.rcp1.domain.recruitment.domain.Post;
import com.example.rcp1.domain.recruitment.dto.PostReqDTO;
import com.example.rcp1.domain.recruitment.dto.PostResDTO;
import com.example.rcp1.domain.user.dto.UpdateProfileReq;
import com.example.rcp1.global.BaseResponse;
import com.example.rcp1.global.ErrorCode;
import com.example.rcp1.global.SuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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
            if (post != null) {
                return ResponseEntity.ok(BaseResponse.success(SuccessCode.POST_CREATED_SUCCESS, post));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(BaseResponse.error(ErrorCode.FORBIDDEN, "채용공고를 작성할 권한이 없습니다."));
            }
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

    //선택된 필터들에 해당하는 채용공고 리스트 조회
    @GetMapping("/posts/search/filters")
    public ResponseEntity<BaseResponse<List<PostResDTO>>> retrieveRecruitmentPostsByFilters(@RequestParam(value = "field", required = false) Set<String> fields,
                                                                                            @RequestParam(value = "career", required = false) Integer career){
        try {
            List<PostResDTO> posts = recruitmentService.retrieveRecruitmentPostsByFilters(fields,career);
            if (!posts.isEmpty()) {
                return ResponseEntity.ok(BaseResponse.success(SuccessCode.POST_RETRIEVAL_SUCCESS, posts));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(BaseResponse.error(ErrorCode.NOT_FOUND, "해당 조건에 맞는 채용공고를 찾을 수 없습니다."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponse.error(ErrorCode.REQUEST_VALIDATION_EXCEPTION, "채용공고 조회에 실패했습니다."));
        }
    }

    //특정 검색어(키워드) 포함하는 채용공고 리스트 조회
    @GetMapping("/posts/search")
    public ResponseEntity<BaseResponse<List<PostResDTO>>> retrieveRecruitmentPostsByKeyword(@RequestParam(value = "keyword", required = true) String keyword) {
        try {
            List<PostResDTO> posts = recruitmentService.retrieveRecruitmentPostsByKeyword(keyword);
            if (!posts.isEmpty()) {
                return ResponseEntity.ok(BaseResponse.success(SuccessCode.POST_RETRIEVAL_SUCCESS, posts));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(BaseResponse.error(ErrorCode.NOT_FOUND, "해당 검색어에 맞는 채용공고를 찾을 수 없습니다."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponse.error(ErrorCode.REQUEST_VALIDATION_EXCEPTION, "채용공고 조회에 실패했습니다."));
        }
    }

    //특정 채용공고 수정
    @PatchMapping("/posts/{postId}")
    public ResponseEntity<BaseResponse<PostResDTO>> updateRecruitmentPostById(@RequestHeader("Authorization") String Authorization,
                                                                              @Valid @RequestBody PostReqDTO postReqDTO,@PathVariable Long postId){
        try {
            String token = Authorization.substring(7);
            PostResDTO post = recruitmentService.updateRecruitmentPostById(token,postReqDTO,postId);
            if (post != null) {
                return ResponseEntity.ok(BaseResponse.success(SuccessCode.POST_UPDATED_SUCCESS, post));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(BaseResponse.error(ErrorCode.FORBIDDEN, "채용공고를 수정할 권한이 없습니다."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponse.error(ErrorCode.REQUEST_VALIDATION_EXCEPTION, "채용공고 수정에 실패했습니다."));
        }
    }


    //특정 채용공고 논리삭제
    @PatchMapping("/posts/{postId}/delete")
    public ResponseEntity<BaseResponse<Post>> deleteRecruitmentPostById(@RequestHeader("Authorization") String Authorization, @PathVariable Long postId){
        try {
            String token = Authorization.substring(7);
            Post post = recruitmentService.deleteRecruitmentPostById(token,postId);
            if (post != null) {
                return ResponseEntity.ok(BaseResponse.success(SuccessCode.LOGICAL_DELETE_SUCCESS, post));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(BaseResponse.error(ErrorCode.FORBIDDEN, "채용공고를 삭제할 권한이 없습니다."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponse.error(ErrorCode.REQUEST_VALIDATION_EXCEPTION, "채용공고 삭제에 실패했습니다."));
        }
    }

}
