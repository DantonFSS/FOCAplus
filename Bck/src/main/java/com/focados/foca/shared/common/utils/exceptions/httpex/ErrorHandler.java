package com.focados.foca.shared.common.utils.exceptions.httpex;

import com.focados.foca.shared.common.utils.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<ApplicationError> handleEmailAlreadyUsedException(
            EmailAlreadyUsedException ex,
            HttpServletRequest request
    ) {
        log.error("Email already used - {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ApplicationError(
                        request,
                        HttpStatus.BAD_REQUEST,
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(CpfAlreadyExistsException.class)
    public ResponseEntity<ApplicationError> cpfAlreadyExistsException(
            RuntimeException ex,
            HttpServletRequest request) {

        log.error("CPF already exists - ", ex);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ApplicationError(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ApplicationError> usernameAlreadyExistsException(
            RuntimeException ex,
            HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ApplicationError(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApplicationError> invalidCredentialsException(
            RuntimeException ex,
            HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApplicationError(request, HttpStatus.UNAUTHORIZED, ex.getMessage()));
    }
    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<ApplicationError> invalidRefreshTokenException(
            RuntimeException ex,
            HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApplicationError(
                        request,
                        HttpStatus.UNAUTHORIZED,
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(UserNotAuthenticatedException.class)
    public ResponseEntity<ApplicationError> userNotAuthenticatedException(
            RuntimeException ex,
            HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApplicationError(
                        request,
                        HttpStatus.UNAUTHORIZED,
                        ex.getMessage()
                ));
    }



}
