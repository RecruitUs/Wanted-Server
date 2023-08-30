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
    LOGICAL_DELETE_SUCCESS(OK, "논리적으로 삭제 되었습니다."),
    POST_CREATED_SUCCESS(CREATED, "채용공고 생성에 성공했습니다."),
    CREATE_APPLY_SUCCESS(OK, "채용 공고에 지원서를 제출했습니다."),
    DELETE_APPLY_SUCCESS(OK, "채용 공고에 대한 지원을 취소했습니다." ),
    GET_STATUS_SUCCESS(OK, "지원 현황 조회 성공"),
    STATUS_SETTING_SUCCESS(OK, "지원자의 상태가 변경되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;


    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
