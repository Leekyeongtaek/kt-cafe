package com.mrlee.ktcafe.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class ApiExceptionController {

    @ExceptionHandler(Exception.class)
    ResponseEntity<String> returnException(Exception e) {
        log.info("error Cause, :: {} Message :: {}", e.getCause(), e.getMessage());
        String msg = "서버 내부에 오류가 발생했습니다. 잠시후 다시 시도해주세요";
        return new ResponseEntity<>(msg, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> returnIllegalArgumentException(IllegalArgumentException e) {
        String msg = (e.getMessage() == null) ? "잘못된 요청입니다. 요청 사항을 다시 확인해주세요." : e.getMessage();
        return new ResponseEntity<>(msg, BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<String> returnJsonMappingException(HttpMessageNotReadableException e) {
        String msg = "잘못된 데이터 형식입니다. 요청 사항을 다시 확인해주세요.";
        return new ResponseEntity<>(msg, BAD_REQUEST);
    }
}
