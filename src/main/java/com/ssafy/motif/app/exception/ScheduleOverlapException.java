package com.ssafy.motif.app.exception;

import com.ssafy.motif.app.code.ErrorCode;
import lombok.Getter;

@Getter
public class ScheduleOverlapException extends RuntimeException {

    private final ErrorCode errorCode;

    public ScheduleOverlapException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
