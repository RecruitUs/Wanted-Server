package com.example.rcp1.domain.user.presentation;

import com.example.rcp1.domain.user.application.UserService;
import com.example.rcp1.domain.user.domain.User;
import com.example.rcp1.domain.user.dto.SignInReq;
import com.example.rcp1.domain.user.dto.SignUpReq;
import com.example.rcp1.domain.user.dto.UpdateProfileReq;
import com.example.rcp1.global.BaseResponse;
import com.example.rcp1.global.CustomAuthenticationException;
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
            String token = userService.signIn(signInReq);

            if (token != null) {
                return ResponseEntity.ok(BaseResponse.success(SuccessCode.SIGNIN_SUCCESS, token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(BaseResponse.error(ErrorCode.EXPIRED_TOKEN, "로그인에 실패했습니다."));
            }
        } catch (CustomAuthenticationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponse.error(ErrorCode.REQUEST_VALIDATION_EXCEPTION, e.getMessage()));
        }

//        return ResponseEntity.ok(BaseResponse.success(SuccessCode.SIGNIN_SUCCESS, userService.login("이재혁", "")));
    }




    // 유저 정보 수정
    @PatchMapping("/profile")
    public ResponseEntity<BaseResponse<?>> updateProfile(
            @RequestHeader("Authorization") String Authorization, // 헤더에서 Authorization 값을 받아온다
            @Valid @RequestBody UpdateProfileReq updateProfileReq) {
        try {
            String access_token = Authorization.substring(7); // Bearer 이후 토큰만 파싱

            // 토큰에서 이메일 파싱 후 이메일이랑 updateprofilereq 객체랑 같이 서비스에 보낸 후 수정처리 하는 코드
            User user = userService.updateProfile(access_token, updateProfileReq);


            return ResponseEntity.ok(BaseResponse.success(SuccessCode.UPDATE_PROFILE_SUCCESS, user));

        } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(BaseResponse.error(ErrorCode.REQUEST_VALIDATION_EXCEPTION, "유저 정보 수정에 실패했습니다."));
        }

    }


    // 유저 정보 탈퇴(논리 삭제)
    @PatchMapping("/delete")
    public ResponseEntity<BaseResponse<?>> deleteUser(
            @RequestHeader("Authorization") String authorization
    ) {

        try {
            String token = authorization.substring(7);
            String t = userService.deleteUser(token);

            return ResponseEntity.ok(BaseResponse.success(SuccessCode.LOGICAL_DELETE_SUCCESS));
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponse.error(ErrorCode.EXPIRED_TOKEN));
        }
    }


}
