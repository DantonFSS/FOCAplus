package com.focados.foca.shared.common.utils.exceptions;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException() {
        super("Curso não encontrado.");
    }

    public CourseNotFoundException(String message) {
        super(message);
    }
}
