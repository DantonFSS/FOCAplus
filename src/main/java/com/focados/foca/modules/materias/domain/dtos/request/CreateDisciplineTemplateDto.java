package com.focados.foca.modules.materias.domain.dtos.request;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateDisciplineTemplateDto {
    private UUID periodTemplateId;
    private String name;
    private String notes;
}
