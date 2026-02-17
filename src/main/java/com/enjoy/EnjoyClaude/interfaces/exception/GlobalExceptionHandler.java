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
            final UserNotFoundException ex, final HttpServletRequest request) {
        log.error("NotFound error: ", ex);
        final ErrorViewResponse error = ErrorViewResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .fieldErrors(null)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler({RoleNotFoundException.class})
    public ResponseEntity<ErrorViewResponse> handleRoleNotFoundException(
            final BusinessException ex, final HttpServletRequest request) {
        log.error("NotFound error: ", ex);
        final ErrorViewResponse error = ErrorViewResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .fieldErrors(null)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorViewResponse> handleConflictException(
            final DuplicateEmailException ex, final HttpServletRequest request) {
        log.error("Conflict error: ", ex);
        final ErrorViewResponse error = ErrorViewResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .fieldErrors(null)
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler({InvalidCredentialsException.class, InvalidTokenException.class, ExpiredTokenException.class})
    public ResponseEntity<ErrorViewResponse> handleUnauthorizedException(
            final BusinessException ex, final HttpServletRequest request) {
        log.error("Unauthorized error: ", ex);
        final ErrorViewResponse error = ErrorViewResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .fieldErrors(null)
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler({AccountDisabledException.class, PermissionDeniedException.class})
    public ResponseEntity<ErrorViewResponse> handleForbiddenException(
            final BusinessException ex, final HttpServletRequest request) {
        log.error("Forbidden error: ", ex);
        final ErrorViewResponse error = ErrorViewResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .fieldErrors(null)
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorViewResponse> handleValidationException(
            final MethodArgumentNotValidException ex, final HttpServletRequest request) {
        log.error("Validation error: ", ex);
        final Map<String, String> fieldErrors = new HashMap<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }

        final ErrorViewResponse error = ErrorViewResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("유효성 검증에 실패했습니다.")
                .path(request.getRequestURI())
                .fieldErrors(fieldErrors)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorViewResponse> handleGenericException(
            final Exception ex, final HttpServletRequest request) {
        log.error("Unexpected error: ", ex);
        final ErrorViewResponse error = ErrorViewResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("서버 내부 오류가 발생했습니다.")
                .path(request.getRequestURI())
                .fieldErrors(null)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
