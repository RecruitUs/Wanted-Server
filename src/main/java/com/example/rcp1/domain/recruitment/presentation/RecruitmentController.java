package com.example.rcp1.domain.recruitment.presentation;


import com.example.rcp1.domain.recruitment.application.RecruitmentService;
import com.example.rcp1.domain.recruitment.domain.Post;
import com.example.rcp1.domain.recruitment.dto.PostDTO;
import com.example.rcp1.global.BaseResponse;
import com.example.rcp1.global.ErrorCode;
import com.example.rcp1.global.SuccessCode;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/recruiting")
@RestController
public class RecruitmentController {

    @Autowired
    private RecruitmentService recruitmentService;


    @PostMapping("/posts")
    public ResponseEntity<BaseResponse<Post>> createRecruitmentPost(@RequestHeader("Authorization") String Authorization, @Valid @RequestBody PostDTO postDTO) {
        try {
            String token = Authorization.substring(7);
            Post post = recruitmentService.createRecruitmentPost(token, postDTO);
            return ResponseEntity.ok(BaseResponse.success(SuccessCode.POST_CREATED_SUCCESS, post));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponse.error(ErrorCode.REQUEST_VALIDATION_EXCEPTION, "채용공고 게시에 실패했습니다."));
        }
    }
}
