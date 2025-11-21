package com.focados.foca.shared.common.utils.exceptions;

public class InvalidCourseDatesException extends RuntimeException {
    public InvalidCourseDatesException() {
        super("A data de início deve ser anterior à data de término do curso.");
    }

    public InvalidCourseDatesException(String message) {
        super(message);
    }
}
