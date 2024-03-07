package com.blog.board.controller;


import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException ex) {
        ErrorResponse error = new ErrorResponse("400", "잘못된 요청입니다");

        List<FieldError> fieldErrors = ex.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            error.addValidation(fieldError.getField(),fieldError.getDefaultMessage());
        }
        return error;
    }

}