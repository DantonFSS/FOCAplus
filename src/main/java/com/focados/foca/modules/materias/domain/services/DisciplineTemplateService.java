package com.focados.foca.modules.materias.domain.services;

import com.focados.foca.modules.materias.domain.dtos.mappers.DisciplineTemplateMapper;
import com.focados.foca.modules.materias.domain.dtos.request.CreateDisciplineTemplateDto;
import com.focados.foca.modules.materias.domain.dtos.request.UpdateDisciplineTemplateDto;
import com.focados.foca.modules.materias.domain.dtos.response.DisciplineTemplateResponseDto;
import com.focados.foca.modules.materias.database.entity.DisciplineTemplateModel;
import com.focados.foca.modules.periods.database.entity.PeriodTemplateModel;
import com.focados.foca.modules.materias.database.repository.DisciplineTemplateRepository;
import com.focados.foca.modules.periods.database.repository.PeriodTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DisciplineTemplateService {

    private final DisciplineTemplateRepository disciplineTemplateRepository;
    private final PeriodTemplateRepository periodTemplateRepository;

    public DisciplineTemplateResponseDto create(CreateDisciplineTemplateDto dto) {
        PeriodTemplateModel periodTemplate = periodTemplateRepository.findById(dto.getPeriodTemplateId())
                .orElseThrow(() -> new IllegalArgumentException("Período não encontrado"));

        DisciplineTemplateModel discipline = new DisciplineTemplateModel();
        discipline.setPeriodTemplate(periodTemplate);
        discipline.setName(dto.getName());
        discipline.setNotes(dto.getNotes());

        disciplineTemplateRepository.save(discipline);
        return DisciplineTemplateMapper.toResponse(discipline);
    }

    public List<DisciplineTemplateResponseDto> getAll() {
        return disciplineTemplateRepository.findAll()
                .stream()
                .map(DisciplineTemplateMapper::toResponse)
                .toList();
    }

    public List<DisciplineTemplateResponseDto> getAllByPeriodTemplateId(UUID periodTemplateId) {
        return disciplineTemplateRepository.findByPeriodTemplateId(periodTemplateId)
                .stream()
                .map(DisciplineTemplateMapper::toResponse)
                .toList();
    }

    public DisciplineTemplateResponseDto getById(UUID id) {
        DisciplineTemplateModel discipline = disciplineTemplateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));
        return DisciplineTemplateMapper.toResponse(discipline);
    }

    public DisciplineTemplateResponseDto update(UUID id, UpdateDisciplineTemplateDto dto) {
        DisciplineTemplateModel discipline = disciplineTemplateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));

        discipline.setName(dto.getName());
        discipline.setNotes(dto.getNotes());

        disciplineTemplateRepository.save(discipline);
        return DisciplineTemplateMapper.toResponse(discipline);
    }

    public void deleteById(UUID id) {
        if (!disciplineTemplateRepository.existsById(id)) {
            throw new IllegalArgumentException("Disciplina não encontrada");
        }
        disciplineTemplateRepository.deleteById(id);
    }
}