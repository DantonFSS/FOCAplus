package com.focados.foca.shared.common.utils.exceptions;

public class UserAlreadyInCourseException extends RuntimeException {
    public UserAlreadyInCourseException() {
        super("Você já participa deste curso.");
    }

    public UserAlreadyInCourseException(String message) {
        super(message);
    }
}
