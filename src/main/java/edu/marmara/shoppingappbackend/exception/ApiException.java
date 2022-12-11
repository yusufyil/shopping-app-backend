package edu.marmara.shoppingappbackend.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiException {
    String message;
    HttpStatus httpStatus;

    public ApiException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
