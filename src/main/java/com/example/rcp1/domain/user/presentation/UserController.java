package com.example.rcp1.domain.user.presentation;

import com.example.rcp1.domain.user.dto.SignUpReq;
import com.example.rcp1.global.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    // 회원가입
    @PostMapping("/signUp")
    public BaseResponse<?> signUp(@Valid @RequestBody SignUpReq signUpReq) {
        return null;
    }


}
