package com.yoanesber.spring.hibernate.one_to_many_postgresql.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.dto.HttpResponseDTO;

/**
 * Custom error controller to handle errors and return a consistent error response.
 * This controller intercepts errors and provides a structured response with status code,
 * message, and details about the error.
 */

@RestController
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {

    @RequestMapping
    public ResponseEntity<HttpResponseDTO> handleError(HttpServletRequest request) {
        // Get the error status code and message
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorDetail = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        // Set the default status code and message
        if (statusCode == null) {
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        } 
        
        String errorMessage = "An unexpected error occurred";
        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            errorMessage = "Resource not found";
            errorDetail = "The requested resource was not found";
        } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
            errorMessage = "Access denied";
            errorDetail = "You don't have permission to access this resource";
        } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
            errorMessage = "Bad request";
            errorDetail = "The request was invalid or cannot be served";
        } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
            errorMessage = "Unauthorized";
            errorDetail = "You need to authenticate to access this resource";
        } else {
            errorDetail = (errorDetail != null) ? errorDetail : "Unexpected error occurred";
        }

        // Return the response
        return new ResponseEntity<>(new HttpResponseDTO(
            errorMessage,
            errorDetail,
            request.getRequestURI(),
            statusCode,
            null
        ), HttpStatus.valueOf(statusCode));
    }
}
