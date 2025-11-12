package com.focados.foca.modules.materias.domain.dtos.mappers;

import com.focados.foca.modules.materias.database.entity.DisciplineInstanceModel;
import com.focados.foca.modules.materias.domain.dtos.response.DisciplineInstanceResponseDto;

import java.util.UUID;

public class DisciplineInstanceMapper {
    public static DisciplineInstanceResponseDto toResponse(DisciplineInstanceModel model) {
        DisciplineInstanceResponseDto dto = new DisciplineInstanceResponseDto();
        dto.setId(model.getId());
        dto.setUserCourseId(model.getUserCourse().getId());
        dto.setDisciplineTemplateId(model.getDisciplineTemplate() != null ? model.getDisciplineTemplate().getId() : null);
        dto.setPeriodInstanceId(model.getPeriodInstance().getId());
        dto.setPlannedStart(model.getPlannedStart());
        dto.setPlannedEnd(model.getPlannedEnd());
        dto.setStatus(model.getStatus().name());
        dto.setGrade(model.getGrade());
        dto.setGradeSystem(model.getGradeSystem().name());
        dto.setAssessmentsCount(model.getAssessmentsCount());
        dto.setCreatedAt(model.getCreatedAt());
        return dto;
    }
}