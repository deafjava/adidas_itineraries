package com.adidas.travel.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ResponseBodyError> noIATAError() {

        return ResponseEntity
                .unprocessableEntity()
                .body(new ResponseBodyError("Invalid iata code!"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseBodyError> genericError() {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseBodyError("Oops! Something wrong happened!"));
    }

    @Data
    @AllArgsConstructor
    private static class ResponseBodyError {
        private String cause;
    }

}
