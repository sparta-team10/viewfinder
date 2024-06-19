package com.sparta.viewfinder.controller;

import com.sparta.viewfinder.exception.CommonErrorCode;
import com.sparta.viewfinder.exception.ErrorCode;
import com.sparta.viewfinder.exception.ErrorResponse;
import com.sparta.viewfinder.exception.MismatchException;
import com.sparta.viewfinder.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionController extends ResponseEntityExceptionHandler {

    // NotFoundException 예외처리
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
        log.warn("Not Found Exception");
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode, e);
    }

    // MismatchException 예외처리
    @ExceptionHandler(MismatchException.class)
    public ResponseEntity<Object> handleMismatchException(MismatchException e) {
        log.warn("Mismatch Exception");
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode, e);
    }

//    // MethodArgumentNotValidException 예외처리
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//        log.warn("Bind Exception");
//        ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
//        return "why";//handleExceptionInternal(errorCode, e);
//    }

    // 그 외 예외처리들
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception e) {
        log.warn("handleAllException: {}", e.getMessage());
        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(errorCode, e);
    }

    // ResponseEntity 생성 함수
    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, Exception e) {
        int value = errorCode.getHttpStatus().value();
        String message = e.getMessage();
        HttpStatus httpStatus = errorCode.getHttpStatus();

        if (message == null) {
            message = errorCode.getMessage();
        }

        return ResponseEntity.status(httpStatus)
                .body(
                        ErrorResponse.builder()
                                .code(value)
                                .message(message)
                                .httpStatus(httpStatus)
                                .build()
                );
    }


    /*private ErrorResponse makeErrorResponse(BindException e, ErrorCode errorCode) {
        List<ErrorResponse.ValidationError> validationErrorList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ErrorResponse.ValidationError::of)
                .collect(Collectors.toList());


        return ErrorResponse.builder()
                .code(errorCode.getHttpStatus().value())
                .message(errorCode.getMessage())
                .httpStatus(errorCode.getHttpStatus())
                .errors(validationErrorList)
                .build();
    }*/
}
