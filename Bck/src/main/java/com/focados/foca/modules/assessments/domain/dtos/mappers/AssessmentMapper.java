package com.focados.foca.modules.assessments.domain.dtos.mappers;


import com.focados.foca.modules.assessments.database.entity.AssessmentModel;
import com.focados.foca.modules.assessments.domain.dtos.response.AssessmentResponseDTO;

public class AssessmentMapper {
    public static AssessmentResponseDTO toResponse(AssessmentModel assessment) {
        AssessmentResponseDTO dto = new AssessmentResponseDTO();
        dto.setId(assessment.getId());
        dto.setTitle(assessment.getTitle());
        dto.setDescription(assessment.getDescription());
        dto.setPointsPossible(assessment.getPointsPossible());
        dto.setDate(assessment.getDate());
        dto.setStartTime(assessment.getStartTime());
        dto.setEndTime(assessment.getEndTime());
        dto.setGrade(assessment.getGrade());
        dto.setDisciplineInstanceId(assessment.getDisciplineInstance().getId());
        return dto;
    }
}