package com.fastcampus.reserve.common.response;

import com.fastcampus.reserve.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<CommonResponse<Void>> handleBaseException(CustomException e) {
        log.error("[CustomException] Message = {}", e.getErrorCode());
        return new ResponseEntity<>(
                CommonResponse.fail(e.getMessage()),
                e.getErrorCode().getHttpStatus()
        );
    }
}
