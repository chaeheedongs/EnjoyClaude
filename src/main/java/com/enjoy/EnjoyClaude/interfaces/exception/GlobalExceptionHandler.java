package com.enjoy.EnjoyClaude.interfaces.exception;

import com.enjoy.EnjoyClaude.domains.common.exception.*;
import com.enjoy.EnjoyClaude.interfaces.dto.response.ErrorViewResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorViewResponse> handleNotFoundException(
            UserNotFoundException ex, HttpServletRequest request) {
        log.error("NotFound error: ", ex);
        ErrorViewResponse error = new ErrorViewResponse(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler({RoleNotFoundException.class})
    public ResponseEntity<ErrorViewResponse> handleRoleNotFoundException(
            BusinessException ex, HttpServletRequest request) {
        log.error("NotFound error: ", ex);
        ErrorViewResponse error = new ErrorViewResponse(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorViewResponse> handleConflictException(
            DuplicateEmailException ex, HttpServletRequest request) {
        log.error("Conflict error: ", ex);
        ErrorViewResponse error = new ErrorViewResponse(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler({InvalidCredentialsException.class, InvalidTokenException.class, ExpiredTokenException.class})
    public ResponseEntity<ErrorViewResponse> handleUnauthorizedException(
            BusinessException ex, HttpServletRequest request) {
        log.error("Unauthorized error: ", ex);
        ErrorViewResponse error = new ErrorViewResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler({AccountDisabledException.class, PermissionDeniedException.class})
    public ResponseEntity<ErrorViewResponse> handleForbiddenException(
            BusinessException ex, HttpServletRequest request) {
        log.error("Forbidden error: ", ex);
        ErrorViewResponse error = new ErrorViewResponse(
                HttpStatus.FORBIDDEN.value(),
                "Forbidden",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorViewResponse> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.error("Validation error: ", ex);
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }

        ErrorViewResponse error = new ErrorViewResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "유효성 검증에 실패했습니다.",
                request.getRequestURI(),
                fieldErrors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorViewResponse> handleGenericException(
            Exception ex, HttpServletRequest request) {
        log.error("Unexpected error: ", ex);
        ErrorViewResponse error = new ErrorViewResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "서버 내부 오류가 발생했습니다.",
                request.getRequestURI(),
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
