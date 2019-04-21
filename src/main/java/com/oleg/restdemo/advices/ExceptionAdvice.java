package com.oleg.restdemo.advices;

import com.oleg.restdemo.exceptions.ResponseResultException;
import com.oleg.restdemo.exceptions.TaskNotFoundException;
import com.oleg.restdemo.exceptions.UserAlreadyExistsException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {
    @Data
    @AllArgsConstructor
    private static class ErrorResponse {
        private String error;
    }

    @Data
    @AllArgsConstructor
    private static class ResponseResult {
        private String result;
    }

    @ExceptionHandler(ResponseResultException.class)
    public ResponseResult responseResultHandler(ResponseResultException e) {
        return new ResponseResult("ok");
    }

    @ExceptionHandler({IllegalAccessException.class, TaskNotFoundException.class, UserAlreadyExistsException.class})
    public ErrorResponse illegalAccessHandler(RuntimeException e) {
        return new ErrorResponse(e.getMessage());
    }
}
