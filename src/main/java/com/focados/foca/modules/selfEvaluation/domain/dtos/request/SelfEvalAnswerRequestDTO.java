package com.focados.foca.modules.selfEvaluation.domain.dtos.request;

import lombok.Data;
import java.util.UUID;

@Data
public class SelfEvalAnswerRequestDTO {
    private UUID sessionId;
    private UUID questionId;
    private Integer score; // 1..10
}