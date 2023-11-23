package com.ssafy.motif.app.exception;

import com.ssafy.motif.app.code.ErrorCode;
import lombok.Getter;

@Getter
public class NotFoundTimetableException extends RuntimeException {

    private final ErrorCode errorCode;

    public NotFoundTimetableException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
