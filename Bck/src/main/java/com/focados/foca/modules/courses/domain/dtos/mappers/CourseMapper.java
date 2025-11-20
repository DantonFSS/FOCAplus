package com.focados.foca.modules.courses.domain.dtos.mappers;

import com.focados.foca.modules.courses.database.entity.CourseModel;
import com.focados.foca.modules.courses.database.entity.UserCourseModel;
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
        course.setDivisionsCount(dto.getDivisionsCount());
        course.setInstitutionName(dto.getInstitutionName());
        course.setStartDate(dto.getStartDate());
        course.setEndDate(dto.getEndDate());
        course.setAddress(dto.getAddress());
        course.setOnline(dto.isOnline());
        course.setStatus(dto.getStatus());
        course.setPhones(dto.getPhones());
        course.setEmails(dto.getEmails());

        return course;
    }

    public static CourseResponseDto toResponse(CourseModel course) {
        CourseResponseDto response = new CourseResponseDto();
        response.setId(course.getId());
        response.setName(course.getName());
        response.setLevel(course.getLevel());
        response.setDivisionType(course.getDivisionType());
        response.setDivisionsCount(course.getDivisionsCount());
        response.setInstitutionName(course.getInstitutionName());
        response.setStartDate(course.getStartDate());
        response.setEndDate(course.getEndDate());
        response.setAddress(course.getAddress());
        response.setOnline(course.isOnline());
        response.setStatus(course.getStatus());
        response.setPhones(course.getPhones());
        response.setEmails(course.getEmails());
        response.setShareCode(course.getShareCode());
        response.setCreatedBy(course.getCreatedBy() != null ? course.getCreatedBy().getId() : null);
        return response;
    }


    /*public static List<CourseResponseDto> toResponseList(List<UserCourseModel> userCourses) {
        return userCourses.stream()
                .map(CourseMapper::toResponse)
                .collect(Collectors.toList());
    }*/
}

