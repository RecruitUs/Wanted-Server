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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/recruiting")
@RestController
public class RecruitmentController {

    @Autowired
    private RecruitmentService recruitmentService;
    public ResponseEntity<BaseResponse<Post>> createRecruitmentPost(@Valid @RequestBody PostDTO postDTO) {
        try {
            Post post = recruitmentService.createRecruitmentPost(postDTO);
            return ResponseEntity.ok(BaseResponse.success(SuccessCode.CREATE_POST_SUCCESS, post));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponse.error(ErrorCode.REQUEST_VALIDATION_EXCEPTION, "채용공고 게시에 실패했습니다."));
        }
    }
}
