package com.focados.foca.modules.materias.domain.services;

import com.focados.foca.modules.materias.database.repository.DisciplineScheduleRepository;
import com.focados.foca.modules.materias.database.repository.DisciplineInstanceRepository;
import com.focados.foca.modules.materias.domain.dtos.mappers.DisciplineScheduleMapper;
import com.focados.foca.modules.materias.domain.dtos.request.CreateDisciplineScheduleDTO;
import com.focados.foca.modules.materias.domain.dtos.request.UpdateDisciplineScheduleDTO;
import com.focados.foca.modules.materias.domain.dtos.response.DisciplineScheduleResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DisciplineScheduleService {

    private final DisciplineScheduleRepository scheduleRepository;
    private final DisciplineInstanceRepository instanceRepository;
    private final DisciplineScheduleMapper mapper;

    public DisciplineScheduleResponseDTO addSchedule(CreateDisciplineScheduleDTO dto) {
        var instance = instanceRepository.findById(dto.disciplineInstanceId())
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));
        var entity = mapper.toEntity(dto, instance);
        var saved = scheduleRepository.save(entity);
        return mapper.toResponse(saved);
    }

    public List<DisciplineScheduleResponseDTO> getAllSchedules() {
        return scheduleRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public List<DisciplineScheduleResponseDTO> getSchedulesByDisciplineInstanceId(UUID disciplineInstanceId) {
        return scheduleRepository.findAllByDisciplineInstanceId(disciplineInstanceId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public DisciplineScheduleResponseDTO getOneById(UUID id) {
        var entity = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Horário não encontrado"));
        return mapper.toResponse(entity);
    }

    public DisciplineScheduleResponseDTO updateSchedule(UUID id, UpdateDisciplineScheduleDTO dto) {
        var model = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Horário não encontrado"));
        model.setWeekday(dto.weekday());
        model.setStartTime(dto.startTime());
        model.setEndTime(dto.endTime());
        scheduleRepository.save(model);
        return mapper.toResponse(model);
    }

    public void deleteSchedule(UUID id) {
        if (!scheduleRepository.existsById(id)) {
            throw new IllegalArgumentException("Horário não encontrado");
        }
        scheduleRepository.deleteById(id);
    }
}