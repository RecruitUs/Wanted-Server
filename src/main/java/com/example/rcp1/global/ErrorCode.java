package com.example.rcp1.global;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public class ErrorCode {
    // api 만들고 수정하기
//    REQUEST_VALIDATION_EXCEPTION(BAD_REQUEST, "잘못된 요청입니니다."),
//    EXPIRED_TOKEN(UNAUTHORIZED, "만료된 토큰입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
