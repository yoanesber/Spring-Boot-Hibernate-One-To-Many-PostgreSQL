package com.yoanesber.spring.hibernate.one_to_many_postgresql.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.CustomHttpResponse;

@RestController
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {

    @RequestMapping
    public ResponseEntity<CustomHttpResponse> handleError(HttpServletRequest request) {
        // Create a custom error response
        CustomHttpResponse errorDetails = new CustomHttpResponse();

        // Set the error details
        errorDetails.setTimestamp(LocalDateTime.now());

        // Get the error status code and message
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String message = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        // Set the default status code and message
        if (statusCode == null) {
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        } 
        
        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            message = "The requested resource was not found";
        } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
            message = "You don't have permission to access this resource";
        } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
            message = "The request was invalid or cannot be served";
        } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
            message = "You need to authenticate to access this resource";
        } else {
            message = (message != null) ? message : "Unexpected error occurred";
        }

        // Set the error details
        errorDetails.setStatusCode(statusCode);
        errorDetails.setMessage(message);
        errorDetails.setData(null);

        // Return the response
        return new ResponseEntity<>(errorDetails, HttpStatus.valueOf(statusCode));
    }
}
