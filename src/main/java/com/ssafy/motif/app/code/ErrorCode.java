package com.ssafy.motif.app.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
@RequiredArgsConstructor
public enum ErrorCode {

    /* 글로벌 예외 */
    UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 에러가 발생하였습니다."),

    /* 인증&인가 예외 */
    UNAUTHORIZED_ACCESS_ERROR(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다."),
    TOKEN_FORBIDDEN_ERROR(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),

    /* Member */
    EMAIL_DUPLICATE_ERROR(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일입니다."),
    MEMBER_NOT_FOUND_BY_EMAIL(HttpStatus.BAD_REQUEST, "존재하지 않는 이메일입니다."),

    /* TimeTable */
    TIMETABLE_DUPLICATE_ERROR(HttpStatus.BAD_REQUEST, "이미 타임테이블이 존재합니다.");

    private final HttpStatus status;
    private final String message;

}

