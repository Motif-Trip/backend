package com.ssafy.motif.app.exception;

import com.ssafy.motif.app.code.ErrorCode;
import lombok.Getter;

@Getter
public class EndTimeBeforeStartTimeException extends RuntimeException {

    private final ErrorCode errorCode;

    public EndTimeBeforeStartTimeException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
