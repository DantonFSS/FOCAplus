package com.focados.foca.modules.materias.domain.dtos.request;

import com.focados.foca.modules.materias.database.entity.enums.DisciplineStatus;
import com.focados.foca.modules.materias.database.entity.enums.GradeSystem;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateDisciplineInstanceDto {
    private LocalDate plannedStart;
    private LocalDate plannedEnd;
    private DisciplineStatus status;
    private BigDecimal grade;
    private GradeSystem gradeSystem;
    private Integer assessmentsCount;
}