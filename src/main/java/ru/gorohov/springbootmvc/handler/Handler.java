package ru.gorohov.springbootmvc.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class Handler {


    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<ValidationErrorResponse> onMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        final List<MessageErrorOnField> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new MessageErrorOnField("BAD REQUEST", error.getField(), error.getDefaultMessage(), LocalDateTime.now()))
                .collect(Collectors.toList());
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(violations);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    protected ResponseEntity<DefaultError> onNoSuchElementException(Exception e) {
        var er = new DefaultError("NOT FOUND", e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(er);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<DefaultError> onIllegalArgumentException(IllegalArgumentException e) {
        var er = new DefaultError("BAD REQUEST", e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<DefaultError> handleException(Exception e) {
        var message = new DefaultError(
                "SERVER ERROR",
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(message);
    }

    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<DefaultError> handleDataIntegrityViolationException(Exception ex) {

        var message = new DefaultError("CONFLICT", ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

}