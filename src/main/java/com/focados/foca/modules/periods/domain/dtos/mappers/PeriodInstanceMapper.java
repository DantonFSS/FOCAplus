package com.focados.foca.modules.periods.domain.dtos.mappers;

import com.focados.foca.modules.periods.database.entity.PeriodInstanceModel;
import com.focados.foca.modules.periods.database.entity.PeriodTemplateModel;
import com.focados.foca.modules.periods.domain.dtos.response.PeriodInstanceResponseDto;

public class PeriodInstanceMapper {
    public static PeriodInstanceResponseDto toResponse(PeriodInstanceModel model) {
        PeriodInstanceResponseDto dto = new PeriodInstanceResponseDto();
        dto.setId(model.getId());
        dto.setUserCourseId(model.getUserCourse().getId());
        dto.setPeriodTemplateId(
                model.getPeriodTemplate() != null ? model.getPeriodTemplate().getId() : null
        );
        dto.setName(model.getName());
        dto.setPosition(model.getPosition());
        dto.setPlannedStart(model.getPlannedStart());
        dto.setPlannedEnd(model.getPlannedEnd());
        return dto;
    }

    public static PeriodTemplateModel toTemplateAux(PeriodInstanceModel instance) {
        PeriodTemplateModel template = new PeriodTemplateModel();
        template.setPlannedStart(instance.getPlannedStart());
        template.setPlannedEnd(instance.getPlannedEnd());
        template.setName(instance.getName());
        template.setPosition(instance.getPosition());
        // Preencher outros campos se necessário
        return template;
    }
}