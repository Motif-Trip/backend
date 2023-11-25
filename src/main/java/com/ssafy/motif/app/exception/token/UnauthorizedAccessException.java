package com.ssafy.motif.app.exception.token;

import com.ssafy.motif.app.code.ErrorCode;
import lombok.Getter;

@Getter
public class UnauthorizedAccessException extends RuntimeException{

    private final ErrorCode errorCode;

    public UnauthorizedAccessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
