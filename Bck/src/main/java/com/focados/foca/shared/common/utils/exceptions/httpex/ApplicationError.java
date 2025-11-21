package com.focados.foca.shared.common.utils.exceptions.httpex;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ApplicationError {

    private final String path;
    private final String method;
    private final int code;
    private final String status;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Object errors;

    public ApplicationError(HttpServletRequest request, HttpStatus status, String message) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.code = status.value();
        this.status = status.getReasonPhrase();
        this.message = message;
        this.errors = null;
    }
}
