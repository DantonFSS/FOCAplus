package com.focados.foca.modules.selfEvaluation.domain.dtos.mappers;


import com.focados.foca.modules.selfEvaluation.database.entity.SelfEvalQuestionModel;
import com.focados.foca.modules.selfEvaluation.domain.dtos.response.SelfEvalQuestionResponseDTO;

public class SelfEvalQuestionMapper {
    public static SelfEvalQuestionResponseDTO toResponse(SelfEvalQuestionModel question) {
        SelfEvalQuestionResponseDTO dto = new SelfEvalQuestionResponseDTO();
        dto.setId(question.getId());
        dto.setText(question.getText());
        dto.setPosition(question.getPosition());
        return dto;
    }
}