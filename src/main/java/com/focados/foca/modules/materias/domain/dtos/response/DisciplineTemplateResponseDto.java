package com.focados.foca.modules.materias.domain.dtos.response;

import lombok.Data;
import java.util.UUID;

@Data
public class DisciplineTemplateResponseDto {
    private UUID id;
    private UUID periodTemplateId;
    private String name;
    private String notes;
}