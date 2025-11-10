package com.focados.foca.modules.periods.domain.dtos.mappers;

import com.focados.foca.modules.periods.database.entity.PeriodTemplateModel;
import com.focados.foca.modules.periods.domain.dtos.response.PeriodTemplateResponseDto;

public class PeriodTemplateMapper {
    public static PeriodTemplateResponseDto toResponse(PeriodTemplateModel model) {
        PeriodTemplateResponseDto dto = new PeriodTemplateResponseDto();
        dto.setId(model.getId());
        dto.setCourseTemplateId(model.getCourseTemplate().getId());
        dto.setName(model.getName());
        dto.setPosition(model.getPosition());
        dto.setPlannedStart(model.getPlannedStart());
        dto.setPlannedEnd(model.getPlannedEnd());
        return dto;
    }
}