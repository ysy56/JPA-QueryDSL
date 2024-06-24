package com.sparta.greeypeople.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * 클라이언트가 유효한 인증 제공을 하지 못한 경우
     * @param ex : UnauthorizedException[custom]
     * @return : error message, HttpStatus.UNAUTHORIZED => 401
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> unauthorizedException(UnauthorizedException ex) {
        log.error("{}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    /**
     * 인증은 되었으나 접근 권한이 없는 경우
     * @param ex : ForbiddenException[custom]
     * @return : error message, HttpStatus.FORBIDDEN => 403
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> forbiddenException(ForbiddenException ex) {
        log.error("{}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    /**
     * Valid 유효성 검사 에러 ( 잘못된 요청에 대한 값이 들어온 경우 )
     * @param ex : MethodArgumentNotValidException
     * @return : error message, HttpStatus.BAD_REQUEST => 400
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("{}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * 중복된 좋아요&팔로우가 눌렸을 경우[unique 제약 조건 위배 에러]
     * @param ex : ViolatedException[custom]
     * @return : HttpStatus.CONFLICT => 409 : 클라이언트 요청에 대한 서버간 충돌
     */
    @ExceptionHandler(ViolatedException.class)
    public ResponseEntity<String> violatedLikeException(ViolatedException ex) {
        log.error("{}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * 입력된 값이 유효하지 않은 경우
     * @param ex : InvalidEnteredException[custom]
     * @return : error message, HttpStatus.BAD_REQUEST => 400
     */
    @ExceptionHandler(InvalidEnteredException.class)
    public ResponseEntity<String> invalidEnteredException(InvalidEnteredException ex) {
        log.error("{}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
