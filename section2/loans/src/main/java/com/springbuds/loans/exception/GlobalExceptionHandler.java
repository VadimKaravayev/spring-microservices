package com.springbuds.loans.exception;

import com.springbuds.loans.dto.ErrorResponseDto;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = ex.getBindingResult().getAllErrors().stream().collect(Collectors.toMap(
                error -> ((FieldError) error).getField(),
                error -> Objects.requireNonNullElse(error.getDefaultMessage(), "")
        ));
        System.out.println("handleMethodArgumentNotValid");
        return ResponseEntity.badRequest().body(errors);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception ex, WebRequest request) {
        System.out.println("handleGlobalException");
        return ResponseEntity.internalServerError()
                .body(ErrorResponseDto.builder()
                        .apiPath(request.getDescription(false))
                        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR)
                        .errorMessage(ex.getMessage())
                        .errorTime(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(Exception ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDto.builder()
                        .apiPath(request.getDescription(false))
                        .errorCode(HttpStatus.NOT_FOUND)
                        .errorMessage(ex.getMessage())
                        .errorTime(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler({LoanAlreadyExistsException.class})
    public ResponseEntity<ErrorResponseDto> handleLoanAlreadyExistsException(Exception ex, WebRequest request) {
        return ResponseEntity
                .badRequest()
                .body(ErrorResponseDto.builder()
                        .apiPath(request.getDescription(false))
                        .errorCode(HttpStatus.BAD_REQUEST)
                        .errorMessage(ex.getMessage())
                        .errorTime(LocalDateTime.now())
                        .build());
    }
}
