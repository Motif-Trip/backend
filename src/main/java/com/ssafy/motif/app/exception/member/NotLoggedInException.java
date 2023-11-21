package com.ssafy.motif.app.exception.member;

import com.ssafy.motif.app.code.ErrorCode;
import lombok.Getter;

@Getter
public class NotLoggedInException extends RuntimeException {

    private final ErrorCode errorCode;

    public NotLoggedInException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
