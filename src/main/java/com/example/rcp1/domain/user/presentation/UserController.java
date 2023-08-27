package com.example.rcp1.domain.user.presentation;

import com.example.rcp1.domain.user.application.UserService;
import com.example.rcp1.domain.user.domain.User;
import com.example.rcp1.domain.user.dto.SignInReq;
import com.example.rcp1.domain.user.dto.SignUpReq;
import com.example.rcp1.global.BaseResponse;
import com.example.rcp1.global.ErrorCode;
import com.example.rcp1.global.SuccessCode;
import io.swagger.models.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signUp")
    public ResponseEntity<BaseResponse<User>> signUp(@Valid @RequestBody SignUpReq signUpReq) {
        try {
            User user = userService.signUp(signUpReq);
            return ResponseEntity.ok(BaseResponse.success(SuccessCode.SIGNUP_SUCCESS, user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponse.error(ErrorCode.REQUEST_VALIDATION_EXCEPTION, "회원가입에 실패했습니다."));
        }
    }


    // 로그인 - access-token 발급 성공
    @PostMapping("/signIn")
    public ResponseEntity<BaseResponse<String>> signIn(@Valid @RequestBody SignInReq signInReq) {
        try {
            String token = userService.login(signInReq.getEmail(), signInReq.getPassword());
            return ResponseEntity.ok(BaseResponse.success(SuccessCode.SIGNIN_SUCCESS, token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponse.error(ErrorCode.REQUEST_VALIDATION_EXCEPTION, "로그인에 실패했습니다."));
        }
//        return ResponseEntity.ok(BaseResponse.success(SuccessCode.SIGNIN_SUCCESS, userService.login("이재혁", "")));
    }

    // 테스트용 인가 글쓰기 (삭제 예정)
    @PostMapping("/write")
    public ResponseEntity<String> writeReview(Authentication authentication) {
        return ResponseEntity.ok().body(authentication.getName() + "님의 글작성이 완료되었습니다.");
    }





}
