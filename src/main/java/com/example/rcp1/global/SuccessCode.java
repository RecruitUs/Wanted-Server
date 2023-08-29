package com.example.rcp1.global;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    // api 만들고 수정하기
//    CUSTOM_SUCCESS(OK, "~ 조회에 성공했습니다."),
//    CUSTOM_CREATED_SUCCESS(CREATED, "~ 생성에 성공했습니다.");
    SIGNUP_SUCCESS(OK, "회원가입에 성공했습니다."),
    SIGNIN_SUCCESS(OK, "로그인에 성공했습니다."),
    UPDATE_PROFILE_SUCCESS(OK, "프로필이 성공적으로 수정되었습니다."),
    LOGICAL_DELETE_SUCCESS(OK, "논리적으로 삭제 되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;



    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
