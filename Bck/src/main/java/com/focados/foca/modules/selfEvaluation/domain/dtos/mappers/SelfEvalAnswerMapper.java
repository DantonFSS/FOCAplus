package com.focados.foca.modules.selfEvaluation.domain.dtos.mappers;


import com.focados.foca.modules.selfEvaluation.database.entity.SelfEvalAnswerModel;
import com.focados.foca.modules.selfEvaluation.domain.dtos.response.SelfEvalAnswerResponseDTO;

public class SelfEvalAnswerMapper {
    public static SelfEvalAnswerResponseDTO toResponse(SelfEvalAnswerModel answer) {
        var dto = new SelfEvalAnswerResponseDTO();
        dto.setId(answer.getId());
        dto.setSessionId(answer.getSession().getId());
        dto.setQuestionId(answer.getQuestion().getId());
        dto.setScore(answer.getScore());
        return dto;
    }
}