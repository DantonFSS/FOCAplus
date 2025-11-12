package com.focados.foca.modules.materias.domain.dtos.mappers;

import com.focados.foca.modules.materias.database.entity.DisciplineTemplateModel;
import com.focados.foca.modules.materias.domain.dtos.response.DisciplineTemplateResponseDto;

public class DisciplineTemplateMapper {
    public static DisciplineTemplateResponseDto toResponse(DisciplineTemplateModel model) {
        DisciplineTemplateResponseDto dto = new DisciplineTemplateResponseDto();
        dto.setId(model.getId());
        dto.setPeriodTemplateId(model.getPeriodTemplate().getId());
        dto.setName(model.getName());
        dto.setNotes(model.getNotes());
        return dto;
    }
}