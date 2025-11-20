package com.focados.foca.modules.selfEvaluation.domain.dtos.request;

import lombok.Data;

@Data
public class SelfEvalQuestionRequestDTO {
    private String text;
    private Integer position;
}