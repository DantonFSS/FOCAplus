package com.focados.foca.modules.periods.domain.services;

import com.focados.foca.modules.courses.database.entity.CourseModel;
import com.focados.foca.modules.courses.database.repository.CourseRepository;
import com.focados.foca.modules.periods.database.entity.PeriodTemplateModel;
import com.focados.foca.modules.periods.database.repository.PeriodTemplateRepository;
import com.focados.foca.modules.courses.database.entity.enums.DivisionType;
import com.focados.foca.modules.periods.domain.dtos.mappers.PeriodTemplateMapper;
import com.focados.foca.modules.periods.domain.dtos.request.CreatePeriodTemplateDto;
import com.focados.foca.modules.periods.domain.dtos.request.UpdatePeriodTemplateDto;
import com.focados.foca.modules.periods.domain.dtos.response.PeriodTemplateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PeriodTemplateService {

    private final PeriodTemplateRepository periodTemplateRepository;
    private final CourseRepository courseRepository;

    private static final Map<DivisionType, String> DIVISION_TYPE_PT = new HashMap<>();
    static {
        DIVISION_TYPE_PT.put(DivisionType.SEMESTER, "SEMESTRE");
        DIVISION_TYPE_PT.put(DivisionType.PERIOD, "PERÍODO");
        DIVISION_TYPE_PT.put(DivisionType.YEAR, "ANO");
        DIVISION_TYPE_PT.put(DivisionType.MODULE, "MÓDULO");
        DIVISION_TYPE_PT.put(DivisionType.QUARTER, "TRIMESTRE");
    }

    public void createPeriodsForCourse(CourseModel course) {
        String prefix = DIVISION_TYPE_PT.getOrDefault(course.getDivisionType(), "PERÍODO");
        for (int i = 1; i <= course.getDivisionsCount(); i++) {
            PeriodTemplateModel period = new PeriodTemplateModel();
            period.setCourseTemplate(course);
            period.setName(prefix + " " + i);
            period.setPosition(i);
            periodTemplateRepository.save(period);
        }
    }

    public PeriodTemplateResponseDto addSemester(CreatePeriodTemplateDto dto) {
        var course = courseRepository.findById(dto.getCourseTemplateId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        List<PeriodTemplateModel> periods = periodTemplateRepository
                .findByCourseTemplateIdOrderByPositionAsc(dto.getCourseTemplateId());
        int nextPosition = periods.size() + 1;

        String prefix = DIVISION_TYPE_PT.getOrDefault(course.getDivisionType(), "PERÍODO");
        String name = prefix + " " + nextPosition;

        PeriodTemplateModel newPeriod = new PeriodTemplateModel();
        newPeriod.setCourseTemplate(course);
        newPeriod.setName(name);
        newPeriod.setPosition(nextPosition);
        newPeriod.setPlannedStart(dto.getPlannedStart());
        newPeriod.setPlannedEnd(dto.getPlannedEnd());

        periodTemplateRepository.save(newPeriod);
        return PeriodTemplateMapper.toResponse(newPeriod);
    }

    public List<PeriodTemplateResponseDto> getAll() {
        return periodTemplateRepository.findAll()
                .stream()
                .map(PeriodTemplateMapper::toResponse)
                .toList();
    }

    public PeriodTemplateResponseDto getById(UUID id) {
        PeriodTemplateModel period = periodTemplateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Período não encontrado"));
        return PeriodTemplateMapper.toResponse(period);
    }

    public PeriodTemplateResponseDto update(UUID id, UpdatePeriodTemplateDto dto) {
        PeriodTemplateModel period = periodTemplateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Período não encontrado"));

        period.setName(dto.getName());
        period.setPlannedStart(dto.getPlannedStart());
        period.setPlannedEnd(dto.getPlannedEnd());
        periodTemplateRepository.save(period);

        return PeriodTemplateMapper.toResponse(period);
    }

    public void deleteById(UUID id) {
        if (!periodTemplateRepository.existsById(id)) {
            throw new IllegalArgumentException("Período não encontrado");
        }
        periodTemplateRepository.deleteById(id);
    }
}