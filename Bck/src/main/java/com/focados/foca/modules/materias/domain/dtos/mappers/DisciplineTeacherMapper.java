package com.focados.foca.modules.materias.domain.dtos.mappers;

import com.focados.foca.modules.materias.database.entity.DisciplineTeacherModel;
import com.focados.foca.modules.materias.database.entity.DisciplineInstanceModel;
import com.focados.foca.modules.materias.domain.dtos.request.CreateDisciplineTeacherDTO;
import com.focados.foca.modules.materias.domain.dtos.response.DisciplineTeacherResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DisciplineTeacherMapper {

    public DisciplineTeacherModel toEntity(CreateDisciplineTeacherDTO dto, DisciplineInstanceModel instance) {
        var model = new DisciplineTeacherModel();
        model.setDisciplineInstance(instance);
        model.setTeacherName(dto.teacherName());
        return model;
    }

    public DisciplineTeacherResponseDTO toResponse(DisciplineTeacherModel model) {
        return new DisciplineTeacherResponseDTO(
                model.getId(),
                model.getDisciplineInstance().getId(),
                model.getTeacherName()
        );
    }
}