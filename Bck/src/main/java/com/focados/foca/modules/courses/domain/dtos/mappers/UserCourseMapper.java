package com.focados.foca.modules.courses.domain.dtos.mappers;

import com.focados.foca.modules.courses.database.entity.UserCourseModel;
import com.focados.foca.modules.courses.domain.dtos.response.UserCourseResponseDto;

public class UserCourseMapper {

    public static UserCourseResponseDto toResponse(UserCourseModel uc) {
        var c = uc.getCourseTemplate();
        UserCourseResponseDto dto = new UserCourseResponseDto();

        dto.setUserCourseId(uc.getId());
        dto.setRole(uc.getRole());
        dto.setAccepted(uc.isAccepted());
        dto.setJoinedAt(uc.getJoinedAt());
        dto.setCustomStart(uc.getCustomStart());
        dto.setCustomEnd(uc.getCustomEnd());

        dto.setTemplateId(c.getId());
        dto.setName(c.getName());
        dto.setLevel(c.getLevel());
        dto.setDivisionType(c.getDivisionType());
        dto.setDivisionsCount(c.getDivisionsCount());
        dto.setInstitutionName(c.getInstitutionName());
        dto.setStartDate(c.getStartDate());
        dto.setEndDate(c.getEndDate());
        dto.setAddress(c.getAddress());
        dto.setOnline(c.isOnline());
        dto.setStatus(c.getStatus());
        dto.setPhones(c.getPhones());
        dto.setEmails(c.getEmails());
        dto.setShareCode(c.getShareCode());
        dto.setCreatedBy(c.getCreatedBy() == null ? null : c.getCreatedBy().getId());

        return dto;
    }
}