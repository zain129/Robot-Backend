package com.zain.robot.backend.advice;

import com.zain.robot.backend.exception.NoCommandFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RobotBackendExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NoCommandFoundException.class})
    public ResponseEntity<Object> handleNoCommandFoundException(final NoCommandFoundException ex, final WebRequest request) {
        final String message = "No commands found to execute";
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ClassCastException.class})
    public ResponseEntity<Object> handleClassCastException(final ClassCastException ex, final WebRequest request) {
        final String message = "Exception thrown during class cast";
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Object> handleInternalServerException(final RuntimeException ex, final WebRequest request) {
        logger.error("500 Status Code", ex);
        final String message = "There is some issue with the server. We will resolve it shortly!";
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
