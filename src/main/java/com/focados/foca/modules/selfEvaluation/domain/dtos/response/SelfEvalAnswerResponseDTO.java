package com.focados.foca.modules.selfEvaluation.domain.dtos.response;

import lombok.Data;
import java.util.UUID;

@Data
public class SelfEvalAnswerResponseDTO {
    private UUID id;
    private UUID sessionId;
    private UUID questionId;
    private Integer score;
}