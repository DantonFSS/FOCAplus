package com.focados.foca.modules.courses.domain.dtos.mappers;

import com.focados.foca.modules.courses.database.entity.CourseModel;
import com.focados.foca.modules.courses.domain.dtos.request.CreateCourseDto;
import com.focados.foca.modules.courses.domain.dtos.response.CourseResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseMapper {

    public static CourseModel toEntity(CreateCourseDto dto) {
        CourseModel course = new CourseModel();
        course.setName(dto.getName());
        course.setLevel(dto.getLevel());
        course.setDivisionType(dto.getDivisionType());
        course.setInstitutionName(dto.getInstitutionName());
        course.setStartDate(dto.getStartDate());
        course.setExpectedEndDate(dto.getExpectedEndDate());
        course.setPeriod(dto.getPeriod());
        course.setDivisionsCount(0);
        return course;
    }

    public static CourseResponseDto toResponse(CourseModel course) {
        CourseResponseDto response = new CourseResponseDto();
        response.setId(course.getId());
        response.setName(course.getName());
        response.setLevel(course.getLevel());
        response.setDivisionType(course.getDivisionType());
        response.setInstitutionName(course.getInstitutionName());
        response.setStartDate(course.getStartDate());
        response.setExpectedEndDate(course.getExpectedEndDate());
        response.setPeriod(course.getPeriod());
        return response;
    }

    public static List<CourseResponseDto> toResponseList(List<CourseModel> courses) {
        return courses.stream()
                .map(CourseMapper::toResponse)
                .collect(Collectors.toList());
    }
}

