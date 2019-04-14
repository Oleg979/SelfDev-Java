package com.oleg.restdemo.advices;

import com.oleg.restdemo.exceptions.ResponseResultException;
import com.oleg.restdemo.exceptions.TaskNotFoundException;
import com.oleg.restdemo.exceptions.UserAlreadyExistsException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
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

    @ResponseBody
    @ExceptionHandler(ResponseResultException.class)
    public ResponseEntity<ResponseResult> responseResultHandler(ResponseResultException e) {
        return new ResponseEntity<>(new ResponseResult("ok"), HttpStatus.OK);
    }

    @ResponseBody
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> TaskNotFoundHandler(TaskNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> UserAlreadyExistsHandler(UserAlreadyExistsException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<ErrorResponse> IllegalAccessHandler(IllegalAccessException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()),  HttpStatus.BAD_REQUEST);
    }
}
