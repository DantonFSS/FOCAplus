package com.focados.foca.modules.materias.domain.dtos.mappers;

import com.focados.foca.modules.materias.database.entity.DisciplineScheduleModel;
import com.focados.foca.modules.materias.database.entity.DisciplineInstanceModel;
import com.focados.foca.modules.materias.domain.dtos.request.CreateDisciplineScheduleDTO;
import com.focados.foca.modules.materias.domain.dtos.response.DisciplineScheduleResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class DisciplineScheduleMapper {

    public DisciplineScheduleModel toEntity(CreateDisciplineScheduleDTO dto, DisciplineInstanceModel instance) {
        var model = new DisciplineScheduleModel();
        model.setDisciplineInstance(instance);
        model.setWeekday(dto.weekday());
        model.setStartTime(dto.startTime());
        model.setEndTime(dto.endTime());
        return model;
    }

    public DisciplineScheduleResponseDTO toResponse(DisciplineScheduleModel model) {
        return new DisciplineScheduleResponseDTO(
                model.getId(),
                model.getDisciplineInstance().getId(),
                model.getWeekday(),
                model.getStartTime(),
                model.getEndTime()
        );
    }
}