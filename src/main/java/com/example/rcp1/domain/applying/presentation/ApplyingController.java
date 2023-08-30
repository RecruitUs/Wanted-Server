package com.example.rcp1.domain.applying.presentation;

import com.example.rcp1.domain.applying.application.ApplyingService;
import com.example.rcp1.domain.applying.domain.Applying;
import com.example.rcp1.domain.applying.dto.ApplyReq;
import com.example.rcp1.domain.applying.dto.GetStatusRes;
import com.example.rcp1.domain.applying.dto.SetResultReq;
import com.example.rcp1.global.BaseResponse;
import com.example.rcp1.global.ErrorCode;
import com.example.rcp1.global.SuccessCode;
import com.example.rcp1.global.config.security.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applying")
public class ApplyingController {

    @Value("${SECRET_KEY}")
    private String secret_key;

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


    // 유저의 지원 현황 조회    엔터티 -> DTO 변환 과정 거침
    @GetMapping("/status")
    public ResponseEntity<BaseResponse<?>> getStatus(
            @RequestHeader("Authorization") String Authorization) {
        String token = Authorization.substring(7);

        ArrayList<GetStatusRes> applyings = applyingService.getStatus(token);

        return ResponseEntity.ok(BaseResponse.success(SuccessCode.GET_STATUS_SUCCESS, applyings));
    }


    // 기업 유저의 합불 결정
    @PatchMapping("/setting/{applyingId}")
    public ResponseEntity<BaseResponse<?>> setResult(
            @RequestHeader("Authorization") String Authorization,
            @PathVariable("applyingId") Long applyingId,
            @Valid @RequestBody SetResultReq setResultReq) {

        String token = Authorization.substring(7);

        if (JwtUtil.isExpired(token, secret_key)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(BaseResponse.error(ErrorCode.EXPIRED_TOKEN));
        }

        applyingService.setStatus(token, applyingId, setResultReq);

        return ResponseEntity.ok(BaseResponse.success(SuccessCode.STATUS_SETTING_SUCCESS));


    }
}
