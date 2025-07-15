package com.example.demo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND); // 404 Not Found
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<String> handleInvalidInputException(InvalidInputException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST); // 400 Bad Request
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleInvalidParameterType(MethodArgumentTypeMismatchException ex) {
        String paramName = ex.getName();
        Object receivedValue = ex.getValue();
        Class<?> expectedType = ex.getRequiredType();

        String errorMessage = String.format(
            "Parameter '%s' received value '%s', which is not a valid type. Expected type: %s.",
            paramName,
            receivedValue != null ? receivedValue.toString() : "null",
            expectedType != null ? expectedType.getSimpleName() : "unknown"
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST); // 400 Bad Request
    }

    // Handler genérico para qualquer outra exceção não tratada especificamente
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>("An internal server error occurred.", HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
    }
}
