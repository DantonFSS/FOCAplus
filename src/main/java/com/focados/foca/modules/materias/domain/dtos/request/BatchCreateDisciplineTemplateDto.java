package com.focados.foca.modules.materias.domain.dtos.request;

import lombok.Data;

import java.util.UUID;
import java.util.List;

@Data
public class BatchCreateDisciplineTemplateDto {
    private UUID periodTemplateId;
    private List<String> names;
}