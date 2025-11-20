package com.focados.foca.modules.materias.domain.services;

import com.focados.foca.modules.materias.database.entity.DisciplineTeacherModel;
import com.focados.foca.modules.materias.domain.dtos.mappers.DisciplineTeacherMapper;
import com.focados.foca.modules.materias.database.repository.DisciplineTeacherRepository;
import com.focados.foca.modules.materias.database.repository.DisciplineInstanceRepository;
import com.focados.foca.modules.materias.domain.dtos.request.CreateDisciplineTeacherDTO;
import com.focados.foca.modules.materias.domain.dtos.request.UpdateDisciplineTeacherDTO;
import com.focados.foca.modules.materias.domain.dtos.response.DisciplineTeacherResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DisciplineTeacherService {

    private final DisciplineTeacherRepository teacherRepository;
    private final DisciplineInstanceRepository instanceRepository;
    private final DisciplineTeacherMapper mapper;

    public DisciplineTeacherResponseDTO addTeacher(CreateDisciplineTeacherDTO dto) {
        var instance = instanceRepository.findById(dto.disciplineInstanceId())
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));
        DisciplineTeacherModel model = mapper.toEntity(dto, instance);
        model = teacherRepository.save(model);
        return mapper.toResponse(model);
    }
    public List<DisciplineTeacherResponseDTO> getAllTeachers() {
        return teacherRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public List<DisciplineTeacherResponseDTO> getByDisciplineInstanceId(UUID disciplineInstanceId) {
        return teacherRepository.findAllByDisciplineInstanceId(disciplineInstanceId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public DisciplineTeacherResponseDTO getOneById(UUID teacherId) {
        var entity = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
        return mapper.toResponse(entity);
    }

    public DisciplineTeacherResponseDTO updateTeacher(UUID id, UpdateDisciplineTeacherDTO dto) {
        var model = teacherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
        model.setTeacherName(dto.teacherName());
        model = teacherRepository.save(model);
        return mapper.toResponse(model);
    }

    public void deleteTeacher(UUID id) {
        if (!teacherRepository.existsById(id)) {
            throw new IllegalArgumentException("Professor não encontrado");
        }
        teacherRepository.deleteById(id);
    }
}