package com.example.rcp1.domain.user.presentation;

import com.example.rcp1.domain.user.application.UserService;
import com.example.rcp1.domain.user.domain.User;
import com.example.rcp1.domain.user.dto.SignUpReq;
import com.example.rcp1.global.BaseResponse;
import com.example.rcp1.global.ErrorCode;
import com.example.rcp1.global.SuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello, Postman!");
    }



}
