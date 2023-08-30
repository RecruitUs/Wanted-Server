package com.example.rcp1.domain.applying.presentation;

import com.example.rcp1.domain.applying.application.ApplyingService;
import com.example.rcp1.domain.applying.domain.Applying;
import com.example.rcp1.domain.applying.dto.ApplyReq;
import com.example.rcp1.global.BaseResponse;
import com.example.rcp1.global.ErrorCode;
import com.example.rcp1.global.SuccessCode;
import com.example.rcp1.global.config.security.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applying")
public class ApplyingController {

    private final ApplyingService applyingService;

    // 특정 채용에 지원
    @PostMapping("/{postId}")
    public ResponseEntity<BaseResponse<?>> apply(
            @RequestHeader("Authorization") String Authorization,
            @PathVariable("postId") Long postId,
            @Valid @RequestBody ApplyReq applyReq) {

        try {
            String access_token = Authorization.substring(7);

            Applying applying = applyingService.apply(access_token, postId, applyReq);

            return ResponseEntity.ok(BaseResponse.success(SuccessCode.CREATE_APPLY_SUCCESS));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponse.error(ErrorCode.REQUEST_VALIDATION_EXCEPTION, "해당 채용 공고 정보를 찾을 수 없습니다."));
        }
    }


    // 지원 취소
    @DeleteMapping("/{applyingId}")
    public ResponseEntity<BaseResponse<?>> applyCancel(
            @RequestHeader("Authorization") String Authorization,
            @PathVariable("applyingId") Long applyingId) {

            String token = Authorization.substring(7);

            applyingService.applyCancel(token, applyingId);

            return ResponseEntity.ok(BaseResponse.success(SuccessCode.DELETE_APPLY_SUCCESS));


    }


    // 유저의 지원 현황 조회


    // 기업 유저의 합불 결정


}
