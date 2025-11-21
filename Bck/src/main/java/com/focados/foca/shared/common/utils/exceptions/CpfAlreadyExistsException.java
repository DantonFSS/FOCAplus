package com.focados.foca.shared.common.utils.exceptions;

public class CpfAlreadyExistsException extends RuntimeException {

    public CpfAlreadyExistsException() {
        super("CPF já está em uso");
    }

    public CpfAlreadyExistsException(String message) {
        super(message);
    }
}
