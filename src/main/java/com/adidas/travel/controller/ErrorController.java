package com.adidas.travel.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ErrorController {

    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ResponseBodyError> noIATAError() {

        return ResponseEntity
                .unprocessableEntity()
                .body(new ResponseBodyError("Invalid iata code!"));
    }

    @Data
    @AllArgsConstructor
    private static class ResponseBodyError {
        private String cause;
    }

}
