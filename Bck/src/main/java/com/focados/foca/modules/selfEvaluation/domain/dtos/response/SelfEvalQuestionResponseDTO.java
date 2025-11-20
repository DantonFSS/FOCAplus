package com.focados.foca.modules.selfEvaluation.domain.dtos.response;

import lombok.Data;
import java.util.UUID;

@Data
public class SelfEvalQuestionResponseDTO {
    private UUID id;
    private String text;
    private Integer position;
}