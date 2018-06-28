package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ExceptionResponse> errorMsg = ex.getBindingResult().getFieldErrors().stream()
                .map(message-> ExceptionResponse.builder().errorCode(message.getField()).errorMessage(Collections.singletonList(message.getDefaultMessage())).build())
                .collect(Collectors.toList());
        return handleExceptionInternal(ex, errorMsg, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ExceptionResponse> errorMsg = ex.getBindingResult().getFieldErrors().stream()
                .map(message-> ExceptionResponse.builder().errorCode(message.getField()).errorMessage(Collections.singletonList(message.getDefaultMessage())).build())
                .collect(Collectors.toList());
        return handleExceptionInternal(ex, errorMsg, headers, HttpStatus.BAD_REQUEST, request);
    }
}


@Data
@Builder
@AllArgsConstructor
class ExceptionResponse {
    private String errorCode;
    private List<String> errorMessage;
}