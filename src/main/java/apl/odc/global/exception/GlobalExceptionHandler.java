package apl.odc.global.exception;

import apl.odc.global.common.BaseResponse;
import apl.odc.global.message.FailureMessage;
import apl.odc.global.util.ApiResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BaseResponse<?>> handleBadRequestException(BadRequestException e) {
        return ApiResponseUtil.failure(FailureMessage.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BaseResponse<?>> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e) {
        return ApiResponseUtil.failure(FailureMessage.TYPE_MISMATCH);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<BaseResponse<?>> handleUnauthorizedException(UnauthorizedException e) {
        return ApiResponseUtil.failure(FailureMessage.UNAUTHORIZED);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseResponse<?>> handleNotFoundException(NotFoundException e) {
        return ApiResponseUtil.failure(FailureMessage.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<BaseResponse<?>> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        return ApiResponseUtil.failure(FailureMessage.METHOD_NOT_ALLOWED);
    }

}
