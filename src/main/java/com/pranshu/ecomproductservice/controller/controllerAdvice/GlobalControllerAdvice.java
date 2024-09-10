package com.pranshu.ecomproductservice.controller.controllerAdvice;

import com.pranshu.ecomproductservice.dto.ErrorResponseDTO;
import com.pranshu.ecomproductservice.exception.InvalidTokenException;
import com.pranshu.ecomproductservice.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleProductNotFoundException(Exception ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatusCode(404);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidTokenException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidTokenException(Exception ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatusCode(403);
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
}

// AOP Concept - @ControllerAdvice
// Globally anywhere there's NullPointerException, this will be called