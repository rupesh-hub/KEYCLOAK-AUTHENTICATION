package com.ayush.userservice.exception;

import com.ayush.userservice.pojos.ApiError;
import com.netflix.client.ClientException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.ConnectException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ApiError handleClientNotFoundException(Exception ex, WebRequest webRequest){
        if (ex instanceof ClientException){
            return ApiError.builder()
                    .httpCode(503)
                    .message("Service is not Available")
                    .build();
        }
        return ApiError.builder()
                .httpCode(500)
                .message("Something went wrong")
                .build();
    }
}
