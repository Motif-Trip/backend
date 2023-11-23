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
    UNEXPECTED(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생했습니다."),

    /* 인증&인가 예외 */
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "서비스 이용을 위해 로그인이 필요합니다."),
    TOKEN_FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 부족합니다."),

    /* Member 관련 예외 */
    EMAIL_DUPLICATE(HttpStatus.BAD_REQUEST, "이미 사용 중인 이메일 주소입니다."),
    PASSWORD_MISS_MATCH(HttpStatus.BAD_REQUEST, "이메일 또는 비밀번호가 올바르지 않습니다."),
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "등록되지 않은 이메일 주소입니다."),

    /* Schedule 관련 예외 */
    INVALID_SCHEDULE_TIMING(HttpStatus.BAD_REQUEST, "종료 시간은 시작 시간 이후여야 합니다."),

    /* TimeTable 관련 예외 */
    TIMETABLE_DUPLICATE(HttpStatus.BAD_REQUEST, "동일한 타임테이블이 이미 존재합니다."),
    TIMETABLE_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 타임테이블을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;

}


