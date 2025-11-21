package com.focados.foca.shared.common.utils.exceptions;

public class UserCourseNotFoundException extends RuntimeException {
    public UserCourseNotFoundException() {
        super("User Course não encontrado.");
    }

    public UserCourseNotFoundException(String message) {
        super(message);
    }
}
