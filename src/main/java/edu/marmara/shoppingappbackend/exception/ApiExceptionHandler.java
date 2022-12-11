package edu.marmara.shoppingappbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

        @ExceptionHandler(value = {Exception.class})
        public ResponseEntity<Object> handleException(Exception e) {
            HttpStatus httpStatus = HttpStatus.NOT_FOUND;
            ApiException apiException = new ApiException(
                    e.getMessage() + " * " + "Basaramadik abi -Yusuf",
                    httpStatus
            );
            return new ResponseEntity<>(apiException, httpStatus);
        }
}
