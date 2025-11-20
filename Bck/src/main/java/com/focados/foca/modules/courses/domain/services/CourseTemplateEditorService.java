package com.focados.foca.modules.courses.domain.services;

import com.focados.foca.modules.courses.database.entity.CourseModel;
import com.focados.foca.modules.courses.domain.dtos.request.UpdateCourseDto;
import com.focados.foca.modules.courses.database.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseTemplateEditorService {

    private final CourseRepository courseRepository;

    public boolean updateCourseIfChanged(UpdateCourseDto dto, CourseModel course) {
        boolean changed = false;

        if (dto.getName() != null && !dto.getName().equals(course.getName())) {
            course.setName(dto.getName());
            changed = true;
        }
        if (dto.getLevel() != null && !dto.getLevel().equals(course.getLevel())) {
            course.setLevel(dto.getLevel());
            changed = true;
        }
        if (dto.getDivisionType() != null && !dto.getDivisionType().equals(course.getDivisionType())) {
            course.setDivisionType(dto.getDivisionType());
            changed = true;
        }
        if (dto.getDivisionsCount() != null && !dto.getDivisionsCount().equals(course.getDivisionsCount())) {
            course.setDivisionsCount(dto.getDivisionsCount());
            changed = true;
        }
        if (dto.getInstitutionName() != null && !dto.getInstitutionName().equals(course.getInstitutionName())) {
            course.setInstitutionName(dto.getInstitutionName());
            changed = true;
        }
        if (dto.getStartDate() != null && !dto.getStartDate().equals(course.getStartDate())) {
            course.setStartDate(dto.getStartDate());
            changed = true;
        }
        if (dto.getEndDate() != null && !dto.getEndDate().equals(course.getEndDate())) {
            course.setEndDate(dto.getEndDate());
            changed = true;
        }
        if (dto.getAddress() != null && !dto.getAddress().equals(course.getAddress())) {
            course.setAddress(dto.getAddress());
            changed = true;
        }
        if (dto.getOnline() != null && !dto.getOnline().equals(course.isOnline())) {
            course.setOnline(dto.getOnline());
            changed = true;
        }
        if (dto.getStatus() != null && !dto.getStatus().equals(course.getStatus())) {
            course.setStatus(dto.getStatus());
            changed = true;
        }
        if (dto.getPhones() != null && !dto.getPhones().equals(course.getPhones())) {
            course.setPhones(dto.getPhones());
            changed = true;
        }
        if (dto.getEmails() != null && !dto.getEmails().equals(course.getEmails())) {
            course.setEmails(dto.getEmails());
            changed = true;
        }

        if (changed) {
            courseRepository.save(course);
        }
        return changed;
    }
}