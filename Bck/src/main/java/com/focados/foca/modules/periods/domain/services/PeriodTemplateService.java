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
import com.focados.foca.shared.common.utils.exceptions.InvalidCourseDatesException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        LocalDate courseStart = course.getStartDate();
        LocalDate courseEnd = course.getEndDate();
        int totalPeriods = course.getDivisionsCount();
        DivisionType type = course.getDivisionType();

        if (courseStart == null || courseEnd == null || !courseStart.isBefore(courseEnd)) {
            throw new InvalidCourseDatesException();
        }

        String prefix = DIVISION_TYPE_PT.getOrDefault(type, "PERÍODO");
        LocalDate lastEnd = courseStart.minusDays(1);

        for (int periodIndex = 1; periodIndex <= totalPeriods; periodIndex++) {
            NextPeriodData dates = getNextPeriodDates(type, lastEnd, courseEnd, periodIndex);

            PeriodTemplateModel period = new PeriodTemplateModel();
            period.setCourseTemplate(course);
            period.setName(prefix + " " + periodIndex);
            period.setPosition(periodIndex);
            period.setPlannedStart(dates.start);
            period.setPlannedEnd(dates.end);

            periodTemplateRepository.save(period);
            lastEnd = dates.end;
        }
    }

    public PeriodTemplateResponseDto addSemester(CreatePeriodTemplateDto dto) {
        var course = courseRepository.findById(dto.getCourseTemplateId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        List<PeriodTemplateModel> periods = periodTemplateRepository
                .findByCourseTemplateIdOrderByPositionAsc(dto.getCourseTemplateId());

        PeriodTemplateModel newPeriod = buildNextPeriod(course, periods);

        if (newPeriod == null) {
            throw new IllegalArgumentException("Semestre excede a data final do curso.");
        }

        periodTemplateRepository.save(newPeriod);
        return PeriodTemplateMapper.toResponse(newPeriod);
    }

    public PeriodTemplateModel buildNextPeriod(CourseModel course, List<PeriodTemplateModel> existingPeriods) {
        String prefix = DIVISION_TYPE_PT.getOrDefault(course.getDivisionType(), "PERÍODO");
        int nextPosition = existingPeriods.size() + 1;
        LocalDate courseEnd = course.getEndDate();

        LocalDate lastEnd = existingPeriods.isEmpty()
                ? course.getStartDate().minusDays(1)
                : existingPeriods.get(existingPeriods.size() - 1).getPlannedEnd();

        NextPeriodData dates = getNextPeriodDates(course.getDivisionType(), lastEnd, courseEnd, nextPosition);

        if (dates.start.isAfter(courseEnd)) {
            System.out.println("[buildNextPeriod] Início calculado após término do curso. Não criar mais períodos.");
            return null;
        }

        PeriodTemplateModel period = new PeriodTemplateModel();
        period.setCourseTemplate(course);
        period.setName(prefix + " " + nextPosition);
        period.setPosition(nextPosition);
        period.setPlannedStart(dates.start);
        period.setPlannedEnd(dates.end);

        System.out.println("[buildNextPeriod] NOVO PERÍODO: position=" + period.getPosition()
                + " start=" + period.getPlannedStart()
                + " end=" + period.getPlannedEnd()
                + " name=" + period.getName());

        return period;
    }

    private NextPeriodData getNextPeriodDates(
            DivisionType type,
            LocalDate previousPeriodEnd,
            LocalDate courseEnd,
            int nextPosition
    ) {
        LocalDate start, end;
        int year;

        if (type == DivisionType.SEMESTER) {
            // SEMESTER: Março–Julho, Julho–Dezembro
            if (previousPeriodEnd.getMonthValue() == 12) {
                year = previousPeriodEnd.getYear() + 1;
                start = LocalDate.of(year, 3, 1);
                end = LocalDate.of(year, 7, 15);
            } else if (previousPeriodEnd.getMonthValue() >= 7) {
                year = previousPeriodEnd.getYear();
                start = LocalDate.of(year, 7, 16);
                end = LocalDate.of(year, 12, 20);
            } else {
                year = previousPeriodEnd.getYear();
                start = LocalDate.of(year, 3, 1);
                end = LocalDate.of(year, 7, 15);
            }
        } else if (type == DivisionType.QUARTER) {
            // QUARTER: Trimestres começando após periodEnd
            start = previousPeriodEnd.plusDays(1);
            end = start.plusMonths(3).minusDays(1);
        } else if (type == DivisionType.YEAR) {
            start = previousPeriodEnd.plusDays(1);
            end = start.plusYears(1).minusDays(1);
        } else {
            // Default: PERIOD/MODULE = 6 meses
            start = previousPeriodEnd.plusDays(1);
            end = start.plusMonths(6).minusDays(1);
        }

        if (end.isAfter(courseEnd)) end = courseEnd;
        if (start.isAfter(end)) start = end;

        return new NextPeriodData(start, end);
    }

    // Helper class para retorno dos dados
    private static class NextPeriodData {
        final LocalDate start;
        final LocalDate end;
        NextPeriodData(LocalDate start, LocalDate end) {
            this.start = start;
            this.end = end;
        }
    }

    public List<PeriodTemplateResponseDto> getAll() {
        return periodTemplateRepository.findAll()
                .stream()
                .map(PeriodTemplateMapper::toResponse)
                .toList();
    }

    public List<PeriodTemplateResponseDto> getAllByCourseId(UUID courseTemplateId) {
        return periodTemplateRepository.findByCourseTemplateIdOrderByPositionAsc(courseTemplateId)
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