package com.ssafy.motif.app.handler;

import com.ssafy.motif.app.code.ApiResponse;
import com.ssafy.motif.app.code.ErrorCode;
import com.ssafy.motif.app.exception.TimetableDeduplicationException;
import com.ssafy.motif.app.exception.member.EmailDuplicateException;
import com.ssafy.motif.app.exception.member.NotFoundMemberException;
import com.ssafy.motif.app.exception.member.NotLoggedInException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ApiResponse apiResponse;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> DefaultExceptionHandler(Exception e) {
        e.printStackTrace();
        return apiResponse.error(ErrorCode.UNEXPECTED_ERROR.getMessage());
    }

    @ExceptionHandler(NotLoggedInException.class)
    public ResponseEntity<?> NotLoggedInExceptionHandler(NotLoggedInException e) {
        e.printStackTrace();
        return apiResponse.error(e.getErrorCode().getMessage());
    }

    @ExceptionHandler(EmailDuplicateException.class)
    public ResponseEntity<?> EmailDuplicateExceptionHandler(EmailDuplicateException e) {
        e.printStackTrace();
        return apiResponse.error(e.getErrorCode().getMessage());
    }

    @ExceptionHandler(TimetableDeduplicationException.class)
    public ResponseEntity<?> TimetableDeduplicationExceptionHandler(
        TimetableDeduplicationException e) {
        e.printStackTrace();
        return apiResponse.error(e.getErrorCode().getMessage());
    }

    @ExceptionHandler(NotFoundMemberException.class)
    public ResponseEntity<?> NotFoundMemberExceptionExceptionHandler(NotFoundMemberException e) {
        e.printStackTrace();
        return apiResponse.error(e.getErrorCode().getMessage());
    }

}
