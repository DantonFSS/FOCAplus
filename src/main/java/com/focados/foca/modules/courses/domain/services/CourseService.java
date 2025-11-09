package com.focados.foca.modules.courses.domain.services;

import com.focados.foca.modules.courses.database.entity.CourseModel;
import com.focados.foca.modules.courses.database.repository.CourseRepository;
import com.focados.foca.modules.courses.domain.dtos.mappers.CourseMapper;
import com.focados.foca.modules.courses.domain.dtos.request.CreateCourseDto;
import com.focados.foca.modules.courses.domain.dtos.request.UpdateCourseDto;
import com.focados.foca.modules.courses.domain.dtos.response.CourseResponseDto;
import com.focados.foca.modules.users.database.entity.UserModel;
import com.focados.foca.modules.users.database.repository.UserRepository;
import com.focados.foca.modules.users.domain.services.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final UserCourseService userCourseService;

    public CourseResponseDto create(CreateCourseDto dto) {
        UUID userId = AuthUtil.getAuthenticatedUserId();
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        CourseModel course = CourseMapper.toEntity(dto);
        course.setCreatedBy(user);

        courseRepository.save(course);

        String shareCode = userCourseService.generateShareCode(course.getName());
        userCourseService.createUserCourseLink(user, course, shareCode);


        return CourseMapper.toResponse(course);
    }

    public List<CourseResponseDto> listAllByUser() {
        UUID userId = AuthUtil.getAuthenticatedUserId();
        List<CourseModel> courses = courseRepository.findByCreatedById(userId);
        return courses.stream()
                .map(CourseMapper::toResponse)
                .toList();
    }

    public CourseResponseDto getById(UUID id) {
        CourseModel course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));
        return CourseMapper.toResponse(course);
    }

    public CourseResponseDto update(UUID id, UpdateCourseDto dto) {
        CourseModel course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        course.setName(dto.getName());
        course.setLevel(dto.getLevel());
        course.setDivisionType(dto.getDivisionType());
        course.setDivisionsCount(dto.getDivisionsCount());
        course.setInstitutionName(dto.getInstitutionName());
        course.setStartDate(dto.getStartDate());
        course.setEndDate(dto.getEndDate());
        course.setAddress(dto.getAddress());
        course.setOnline(dto.getOnline());
        course.setStatus(dto.getStatus());
        course.setPhones(dto.getPhones());
        course.setEmails(dto.getEmails());

        courseRepository.save(course);

        return CourseMapper.toResponse(course);
    }

    public void deleteById(UUID id) {
        UUID userId = AuthUtil.getAuthenticatedUserId();
        CourseModel course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));
        if (!course.getCreatedBy().getId().equals(userId)) {
            throw new SecurityException("Apenas owner pode deletar o template.");
        }
        courseRepository.delete(course);
    }

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
