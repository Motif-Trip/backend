package com.ssafy.motif.app.exception;

import com.ssafy.motif.app.code.ErrorCode;
import lombok.Getter;

@Getter
public class TimetableDeduplicationException extends RuntimeException {

    private final ErrorCode errorCode;

    public TimetableDeduplicationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
