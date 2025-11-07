package com.sa.healntrack.pharmacy_service.common.infrastructure.exception;

import com.sa.healntrack.pharmacy_service.common.application.exception.BusinessException;
import com.sa.healntrack.pharmacy_service.common.application.exception.DuplicateEntityException;
import com.sa.healntrack.pharmacy_service.common.application.exception.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.*;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DeserializerException.class)
    ProblemDetail handleMessageDeserializationException(DeserializerException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Deserialization Error");
        problemDetail.setProperty("error_category", "Deserialization");
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(BusinessException.class)
    ProblemDetail handleBusinessException(BusinessException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setTitle("Domain");
        problemDetail.setProperty("error_category", "Business Rule");
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(SerializerException.class)
    ProblemDetail handleMessageSerializationException(SerializerException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Serialization Error");
        problemDetail.setProperty("error_category", "Serialization");
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    ProblemDetail handleEntityNotFound(EntityNotFoundException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        pd.setTitle("Entity Not Found");
        pd.setProperty("error_category", "Business Rule");
        pd.setProperty("timestamp", Instant.now());
        return pd;
    }

    @ExceptionHandler(DuplicateEntityException.class)
    ProblemDetail handleDuplicateEntity(DuplicateEntityException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        pd.setTitle("Duplicate Entity");
        pd.setProperty("error_category", "Business Rule");
        pd.setProperty("timestamp", Instant.now());
        return pd;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String msg = error.getDefaultMessage();
            errors.put(field, msg);
        });

        String detail = errors.entrySet().stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining(", "));

        ProblemDetail pd = ProblemDetail.forStatus(status);
        pd.setTitle("Validation Error");
        pd.setDetail(detail);
        pd.setProperty("error_category", "Validation");
        pd.setProperty("timestamp", Instant.now());
        pd.setProperty("errors", errors);

        return new ResponseEntity<>(pd, headers, status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ProblemDetail handleConstraintViolation(ConstraintViolationException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        pd.setTitle("Constraint Violation");
        pd.setProperty("error_category", "Validation");
        pd.setProperty("timestamp", Instant.now());
        return pd;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ProblemDetail handleIllegalArgument(IllegalArgumentException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        pd.setTitle("Illegal Argument");
        pd.setProperty("error_category", "Domain");
        pd.setProperty("timestamp", Instant.now());
        return pd;
    }

    @ExceptionHandler(IllegalStateException.class)
    ProblemDetail handleIllegalState(IllegalStateException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        pd.setTitle("Illegal State");
        pd.setProperty("error_category", "Domain");
        pd.setProperty("timestamp", Instant.now());
        return pd;
    }

    @ExceptionHandler(NullPointerException.class)
    ProblemDetail handleNullPointer(NullPointerException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected null value: " + e.getMessage());
        pd.setTitle("Null Pointer");
        pd.setProperty("error_category", "Domain");
        pd.setProperty("timestamp", Instant.now());
        return pd;
    }
}
