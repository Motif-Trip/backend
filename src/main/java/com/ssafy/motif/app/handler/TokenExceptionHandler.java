package com.ssafy.motif.app.handler;

import com.ssafy.motif.app.code.ApiResponse;
import com.ssafy.motif.app.code.ErrorCode;
import com.ssafy.motif.app.exception.token.UnauthorizedAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class TokenExceptionHandler {

    private final ApiResponse apiResponse;

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<?> UnauthorizedAccessExceptionHandler(UnauthorizedAccessException e) {
        e.printStackTrace();
        return apiResponse.error(ErrorCode.UNEXPECTED_ERROR.getMessage());
    }
}
