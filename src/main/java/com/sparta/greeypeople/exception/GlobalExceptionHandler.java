package com.sparta.greeypeople.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RestController
public class GlobalExceptionHandler {

    /**
     * 클라이언트로 부터 잘못된 요청 값이 들어온 경우
     * @param ex : BadRequestException[custom]
     * @return : error message, HttpStatus.BAD_REQUEST => 400
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> badRequestException(BadRequestException ex) {
        log.error("{}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * DB 조회 시 값이 존재하지 않는 경우
     * @param ex : DataNotFoundException[custom]
     * @return : error message, HttpStatus.NOT_FOUND => 404
     */
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<String> dataNotFoundException(DataNotFoundException ex) {
        log.error("{}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * 이미 존재하는 값이 있는 경우
     * @param ex : ConflictException[custom]
     * @return : error message, HttpStatus.CONFLICT => 409
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<String> conflictException(ConflictException ex) {
        log.error("{}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

}
