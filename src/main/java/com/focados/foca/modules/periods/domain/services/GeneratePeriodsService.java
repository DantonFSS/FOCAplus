package com.focados.foca.modules.periods.domain.services;

import com.focados.foca.modules.courses.database.entity.CourseModel;
import com.focados.foca.modules.courses.database.entity.enums.DivisionType;
import com.focados.foca.modules.periods.database.entity.PeriodTemplateModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeneratePeriodsService {

   /* public List<PeriodTemplateModel> generatePeriods(CourseModel course) {
        int count = course.getDivisionsCount();
        DivisionType type = course.getDivisionType();

        List<PeriodTemplateModel> periods = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            PeriodTemplateModel period = new PeriodTemplateModel();
            period.setCourseTemplate(course);
            period.setName(type.substring(0,1).toUpperCase() + type.substring(1) + " " + i);  // exemplo: Semestre 1
            period.setPosition(i);
            // plannedStart/plannedEnd podem ser melhorados futuramente
            periods.add(period);
        }

        return periods;
    }*/
}