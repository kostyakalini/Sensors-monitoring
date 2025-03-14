package org.project.ms.monitorsensors.common;

import jakarta.servlet.http.HttpServletRequest;
import org.project.ms.monitorsensors.dto.exception.ErrorInfo;
import org.project.ms.monitorsensors.service.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorInfo> handleResponseStatusException(HttpServletRequest req, ResponseStatusException ex) {
        ErrorInfo errorInfo = new ErrorInfo(req.getRequestURL().toString(), ex.getReason());
        return new ResponseEntity<>(errorInfo, ex.getStatusCode());
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorInfo> handleServiceException(HttpServletRequest req, ServiceException ex) {
        ErrorInfo errorInfo = new ErrorInfo(req.getRequestURL().toString(), ex.getMessage());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> handleException(HttpServletRequest req, Exception ex) {
        ErrorInfo errorInfo = new ErrorInfo(req.getRequestURL().toString(), "Unexpected error: " + ex.getMessage());
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}