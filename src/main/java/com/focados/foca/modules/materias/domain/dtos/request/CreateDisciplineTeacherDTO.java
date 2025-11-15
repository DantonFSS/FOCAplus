package com.focados.foca.modules.materias.domain.dtos.request;
import java.util.UUID;

public record CreateDisciplineTeacherDTO(
        UUID disciplineInstanceId,
        String teacherName
) {}
