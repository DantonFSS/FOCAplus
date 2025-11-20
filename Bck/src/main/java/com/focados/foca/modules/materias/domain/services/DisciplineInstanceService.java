package com.focados.foca.modules.materias.domain.services;

import com.focados.foca.modules.courses.database.entity.CourseModel;
import com.focados.foca.modules.courses.database.entity.UserCourseModel;
import com.focados.foca.modules.courses.database.repository.UserCourseRepository;
import com.focados.foca.modules.materias.database.entity.DisciplineInstanceModel;
import com.focados.foca.modules.materias.database.entity.DisciplineTemplateModel;
import com.focados.foca.modules.materias.database.entity.enums.DisciplineStatus;
import com.focados.foca.modules.materias.database.entity.enums.GradeSystem;
import com.focados.foca.modules.materias.database.repository.DisciplineInstanceRepository;
import com.focados.foca.modules.materias.database.repository.DisciplineTemplateRepository;
import com.focados.foca.modules.materias.domain.dtos.mappers.DisciplineInstanceMapper;
import com.focados.foca.modules.materias.domain.dtos.request.CreateDisciplineInstanceDto;
import com.focados.foca.modules.materias.domain.dtos.request.UpdateDisciplineInstanceDto;
import com.focados.foca.modules.materias.domain.dtos.response.DisciplineInstanceResponseDto;
import com.focados.foca.modules.periods.database.entity.PeriodInstanceModel;
import com.focados.foca.modules.periods.database.entity.PeriodTemplateModel;
import com.focados.foca.modules.periods.database.repository.PeriodInstanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DisciplineInstanceService {

    private final DisciplineTemplateRepository disciplineTemplateRepository;
    private final DisciplineInstanceRepository disciplineInstanceRepository;
    private final UserCourseRepository userCourseRepository;
    private final PeriodInstanceRepository periodInstanceRepository;

    public DisciplineInstanceResponseDto create(CreateDisciplineInstanceDto dto) {
        DisciplineTemplateModel template = disciplineTemplateRepository.findById(dto.getDisciplineTemplateId())
                .orElseThrow(() -> new IllegalArgumentException("Disciplina template não encontrada"));

        UserCourseModel userCourse = userCourseRepository.findById(dto.getUserCourseId())
                .orElseThrow(() -> new IllegalArgumentException("UserCourse não encontrado"));

        PeriodInstanceModel periodInstance = periodInstanceRepository.findById(dto.getPeriodInstanceId())
                .orElseThrow(() -> new IllegalArgumentException("Período não encontrado"));


        DisciplineInstanceModel instance = new DisciplineInstanceModel();
        instance.setDisciplineTemplate(template);
        instance.setUserCourse(userCourse);
        instance.setPeriodInstance(periodInstance);
        instance.setPlannedStart(dto.getPlannedStart());
        instance.setPlannedEnd(dto.getPlannedEnd());
        instance.setAssessmentsCount(dto.getAssessmentsCount() != null ? dto.getAssessmentsCount() : null);
        instance.setStatus(DisciplineStatus.NOT_STARTED);
        instance.setGrade(null);
        instance.setGradeSystem(GradeSystem.NUMERIC_10);
        instance.setCreatedAt(ZonedDateTime.now());

        disciplineInstanceRepository.save(instance);
        return DisciplineInstanceMapper.toResponse(instance);
    }

    public void createDisciplineInstanceForOwner(DisciplineTemplateModel template) {
        PeriodTemplateModel periodTemplate = template.getPeriodTemplate();
        CourseModel course = periodTemplate.getCourseTemplate();

        // Busca o owner do curso
        UserCourseModel ownerUserCourse = userCourseRepository.findByUserIdAndCourseTemplateId(
                course.getCreatedBy().getId(), course.getId()
        ).orElseThrow(() -> new IllegalStateException("Owner UserCourse não encontrado"));

        // Busca o period instance do owner (nesse período)
        PeriodInstanceModel ownerPeriodInstance = periodInstanceRepository.findByUserCourseIdAndPeriodTemplateId(
                ownerUserCourse.getId(), periodTemplate.getId()
        ).orElse(null);

        // Criar a instance
        DisciplineInstanceModel instance = new DisciplineInstanceModel();
        instance.setUserCourse(ownerUserCourse);
        instance.setPeriodInstance(ownerPeriodInstance);
        instance.setDisciplineTemplate(template);
        instance.setPlannedStart(periodTemplate.getPlannedStart());
        instance.setPlannedEnd(periodTemplate.getPlannedEnd());
        instance.setStatus(DisciplineStatus.NOT_STARTED);
        instance.setGrade(null);
        instance.setGradeSystem(GradeSystem.NUMERIC_10);
        instance.setAssessmentsCount(0);
        instance.setCreatedAt(ZonedDateTime.now());

        disciplineInstanceRepository.save(instance);
    }

    public void createDisciplineInstancesForOwnerBatch(List<DisciplineTemplateModel> templates) {
        for (DisciplineTemplateModel template : templates) {
            createDisciplineInstanceForOwner(template);
        }
    }

    public void createDisciplineInstancesForPeriod(PeriodInstanceModel periodInstance, UserCourseModel userCourse) {
        PeriodTemplateModel periodTemplate = periodInstance.getPeriodTemplate();
        List<DisciplineTemplateModel> disciplineTemplates =
                disciplineTemplateRepository.findByPeriodTemplateId(periodTemplate.getId());

        for (DisciplineTemplateModel template : disciplineTemplates) {
            DisciplineInstanceModel instance = new DisciplineInstanceModel();

            instance.setUserCourse(userCourse);
            instance.setPeriodInstance(periodInstance);
            instance.setDisciplineTemplate(template);
            instance.setPlannedStart(periodTemplate.getPlannedStart());
            instance.setPlannedEnd(periodTemplate.getPlannedEnd());
            instance.setStatus(DisciplineStatus.NOT_STARTED);
            instance.setGrade(null);
            instance.setGradeSystem(GradeSystem.NUMERIC_10);
            instance.setAssessmentsCount(0);
            instance.setCreatedAt(ZonedDateTime.now());

            disciplineInstanceRepository.save(instance);
        }
    }

    public List<DisciplineInstanceResponseDto> getAll() {
        return disciplineInstanceRepository.findAll()
                .stream()
                .map(DisciplineInstanceMapper::toResponse)
                .toList();
    }

    public List<DisciplineInstanceResponseDto> getAllByPeriodInstanceId(UUID periodInstanceId) {
        return disciplineInstanceRepository.findByPeriodInstanceId(periodInstanceId)
                .stream()
                .map(DisciplineInstanceMapper::toResponse)
                .toList();
    }

    public DisciplineInstanceResponseDto getById(UUID id) {
        DisciplineInstanceModel instance = disciplineInstanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));
        return DisciplineInstanceMapper.toResponse(instance);
    }

    public DisciplineInstanceResponseDto update(UUID id, UpdateDisciplineInstanceDto dto) {
        DisciplineInstanceModel instance = disciplineInstanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));

        if (dto.getPlannedStart() != null) instance.setPlannedStart(dto.getPlannedStart());
        if (dto.getPlannedEnd() != null) instance.setPlannedEnd(dto.getPlannedEnd());
        if (dto.getStatus() != null) instance.setStatus(dto.getStatus());
        if (dto.getGrade() != null) instance.setGrade(dto.getGrade());
        if (dto.getGradeSystem() != null) instance.setGradeSystem(dto.getGradeSystem());
        if (dto.getAssessmentsCount() != null) instance.setAssessmentsCount(dto.getAssessmentsCount());

        disciplineInstanceRepository.save(instance);
        return DisciplineInstanceMapper.toResponse(instance);
    }

    public void deleteById(UUID id) {
        if (!disciplineInstanceRepository.existsById(id)) {
            throw new IllegalArgumentException("Disciplina não encontrada");
        }
        disciplineInstanceRepository.deleteById(id);
    }
}