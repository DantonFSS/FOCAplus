package com.focados.foca.modules.courses.domain.services;

import com.focados.foca.modules.courses.database.entity.CourseModel;
import com.focados.foca.modules.courses.database.entity.UserCourseModel;
import com.focados.foca.modules.courses.database.entity.enums.UserCourseRole;
import com.focados.foca.modules.courses.database.repository.CourseRepository;
import com.focados.foca.modules.courses.database.repository.UserCourseRepository;
import com.focados.foca.modules.courses.domain.dtos.mappers.UserCourseMapper;
import com.focados.foca.modules.courses.domain.dtos.request.UpdateCourseDto;
import com.focados.foca.modules.courses.domain.dtos.request.UpdateUserCourseDto;
import com.focados.foca.modules.courses.domain.dtos.response.UserCourseResponseDto;
import com.focados.foca.modules.periods.domain.services.PeriodInstanceService;
import com.focados.foca.modules.users.database.entity.UserModel;
import com.focados.foca.modules.users.database.repository.UserRepository;
import com.focados.foca.modules.users.domain.services.AuthUtil;
import com.focados.foca.shared.common.utils.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserCourseService {

    private final UserCourseRepository userCourseRepository;
    private final CourseTemplateEditorService courseTemplateEditorService;
    private final UserRepository userRepository;
    private final PeriodInstanceService periodInstanceService;
    private final CourseRepository courseRepository;

    public UserCourseModel createUserCourseLink(UserModel user, CourseModel course) {
        boolean isOwner = user.getId().equals(course.getCreatedBy().getId());
        UserCourseModel userCourse = new UserCourseModel();
        userCourse.setUser(user);
        userCourse.setCourseTemplate(course);
        userCourse.setRole(isOwner ? UserCourseRole.OWNER : UserCourseRole.MEMBER);
        userCourse.setAccepted(true);
        userCourse.setCustomStart(course.getStartDate());
        userCourse.setCustomEnd(course.getEndDate());
        return userCourseRepository.save(userCourse);
    }

    public List<UserCourseResponseDto> findAllByUser() {
        UUID userId = AuthUtil.getAuthenticatedUserId();
        List<UserCourseModel> userCourses = userCourseRepository.findByUserId(userId);
        return userCourses.stream().map(UserCourseMapper::toResponse).toList();
    }

    public UserCourseResponseDto getById(UUID id) {
        UUID userId = AuthUtil.getAuthenticatedUserId();
        UserCourseModel userCourse = userCourseRepository.findById(id)
                .orElseThrow(UserCourseNotFoundException::new);

        if (!userCourse.getUser().getId().equals(userId)) {
            throw new UserNotAllowedException();
        }

        return UserCourseMapper.toResponse(userCourse);
    }

    public UserCourseResponseDto updateUserCourse(UUID id, UpdateUserCourseDto dto) {
        UUID userId = AuthUtil.getAuthenticatedUserId();
        UserCourseModel userCourse = userCourseRepository.findById(id)
                .orElseThrow(UserCourseNotFoundException::new);
        CourseModel course = userCourse.getCourseTemplate();

        if (!userCourse.getUser().getId().equals(userId)) {
            throw new UserNotAllowedException();
        }

        if (dto.getCustomStart() != null && dto.getCustomEnd() != null) {
            if (dto.getCustomEnd().isBefore(dto.getCustomStart())) {
                throw new InvalidCourseDatesException();
            }
        }

        // Atualiza campos do vínculo
        userCourse.setCustomStart(dto.getCustomStart());
        userCourse.setCustomEnd(dto.getCustomEnd());

        // Monta UpdateCourseDto com campos do DTO de update combinada
        UpdateCourseDto updateCourseDto = new UpdateCourseDto();
        updateCourseDto.setId(course.getId());
        updateCourseDto.setName(dto.getName());
        updateCourseDto.setLevel(dto.getLevel());
        updateCourseDto.setDivisionType(dto.getDivisionType());
        updateCourseDto.setDivisionsCount(dto.getDivisionsCount());
        updateCourseDto.setInstitutionName(dto.getInstitutionName());
        updateCourseDto.setStartDate(dto.getStartDate());
        updateCourseDto.setEndDate(dto.getEndDate());
        updateCourseDto.setAddress(dto.getAddress());
        updateCourseDto.setOnline(dto.getOnline() != null ? dto.getOnline() : course.isOnline());
        updateCourseDto.setStatus(dto.getStatus());
        updateCourseDto.setPhones(dto.getPhones());
        updateCourseDto.setEmails(dto.getEmails());

        // Chama o service centralizado
        courseTemplateEditorService.updateCourseIfChanged(updateCourseDto, course);
        userCourseRepository.save(userCourse);

        return UserCourseMapper.toResponse(userCourse);
    }

    public void deleteUserCourseById(UUID id) {
        UUID userId = AuthUtil.getAuthenticatedUserId();
        UserCourseModel userCourse = userCourseRepository.findById(id)
                .orElseThrow(UserCourseNotFoundException::new);
        if (!userCourse.getUser().getId().equals(userId)) {
            throw new UserNotAllowedException();
        }
        userCourseRepository.delete(userCourse);
    }

    public UserCourseResponseDto joinCourseByShareCode(String shareCode) {
        UUID userId = AuthUtil.getAuthenticatedUserId();
        UserModel user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        CourseModel course = courseRepository.findByShareCode(shareCode)
                .orElseThrow(ShareCodeInvalidException::new);

        boolean alreadyMember = userCourseRepository.existsByUserIdAndCourseTemplateId(
                userId, course.getId());
        if (alreadyMember) throw new UserAlreadyInCourseException();
        UserCourseModel newUserCourse = createUserCourseLink(user, course);
        periodInstanceService.createPeriodInstancesForUserCourse(newUserCourse);
        return UserCourseMapper.toResponse(newUserCourse);
    }

}