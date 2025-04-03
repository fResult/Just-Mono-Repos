package dev.fresult.service.controllers;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@RestControllerAdvice
public class ResponseAdviceHandler extends ResponseEntityExceptionHandler {
  private static final Logger logger = LoggerFactory.getLogger(ResponseAdviceHandler.class);

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode status,
      @NonNull WebRequest request) {

    final var propertyToError = new HashMap<String, Object>();
    final var detail = ProblemDetail.forStatusAndDetail(status, "Invalid request arguments");

    ex.getBindingResult()
        .getFieldErrors()
        .forEach(
            error -> {
              propertyToError.put(error.getField(), error.getDefaultMessage());
            });
    detail.setProperty("arguments", propertyToError);

    return ResponseEntity.of(detail).build();
  }
}
