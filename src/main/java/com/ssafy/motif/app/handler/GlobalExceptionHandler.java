package com.ssafy.motif.app.handler;

import com.ssafy.motif.app.code.ApiResponse;
import com.ssafy.motif.app.code.ErrorCode;
import com.ssafy.motif.app.exception.EndTimeBeforeStartTimeException;
import com.ssafy.motif.app.exception.NotFoundTimetableException;
import com.ssafy.motif.app.exception.ScheduleOverlapException;
import com.ssafy.motif.app.exception.TimetableDeduplicationException;
import com.ssafy.motif.app.exception.member.EmailDuplicateException;
import com.ssafy.motif.app.exception.member.NotFoundMemberException;
import com.ssafy.motif.app.exception.member.NotLoggedInException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ApiResponse apiResponse;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> DefaultExceptionHandler(Exception e) {
        e.printStackTrace();
        return apiResponse.error(ErrorCode.UNEXPECTED.getMessage());
    }

    @ExceptionHandler(NotLoggedInException.class)
    public ResponseEntity<?> NotLoggedInExceptionHandler(NotLoggedInException e) {
        log.error("NotLoggedInException : {}", e.getErrorCode().getMessage());
        return apiResponse.error(e.getErrorCode().getMessage());
    }

    @ExceptionHandler(EmailDuplicateException.class)
    public ResponseEntity<?> EmailDuplicateExceptionHandler(EmailDuplicateException e) {
        log.error("EmailDuplicateException : {}", e.getErrorCode().getMessage());
        return apiResponse.error(e.getErrorCode().getMessage());
    }

    @ExceptionHandler(TimetableDeduplicationException.class)
    public ResponseEntity<?> TimetableDeduplicationExceptionHandler(
        TimetableDeduplicationException e) {
        log.error("TimetableDeduplicationException : {}", e.getErrorCode().getMessage());
        return apiResponse.error(e.getErrorCode().getMessage());
    }

    @ExceptionHandler(NotFoundMemberException.class)
    public ResponseEntity<?> NotFoundMemberExceptionHandler(NotFoundMemberException e) {
        log.error("NotFoundMemberException : {}", e.getErrorCode().getMessage());
        return apiResponse.error(e.getErrorCode().getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> MaxUploadSizeExceededExceptionHandler(
        MaxUploadSizeExceededException e) {
        log.error("MaxUploadSizeExceededException : {}", "파일의 용량이 너무 큽니다.");
        return apiResponse.error("파일의 용량이 너무 큽니다.");
    }

    @ExceptionHandler(NotFoundTimetableException.class)
    public ResponseEntity<?> NotFoundTimetableExceptionHandler(NotFoundTimetableException e) {
        log.error("NotFoundTimetableException : {}", e.getErrorCode().getMessage());
        return apiResponse.error(e.getErrorCode().getMessage());
    }

    @ExceptionHandler(EndTimeBeforeStartTimeException.class)
    public ResponseEntity<?> EndTimeBeforeStartTimeExceptionHandler(
        EndTimeBeforeStartTimeException e) {
        log.error("EndTimeBeforeStartTimeException : {}", e.getErrorCode().getMessage());
        return apiResponse.error(e.getErrorCode().getMessage());
    }

    @ExceptionHandler(ScheduleOverlapException.class)
    public ResponseEntity<?> ScheduleOverlapExceptionHandler(ScheduleOverlapException e) {
        log.error("ScheduleOverlapException : {}", e.getErrorCode().getMessage());
        return apiResponse.error(e.getErrorCode().getMessage());
    }

}
