package com.focados.foca.modules.courses.domain.services;

import com.focados.foca.modules.courses.database.entity.CourseModel;
import com.focados.foca.modules.courses.database.entity.UserCourseModel;
import com.focados.foca.modules.courses.database.repository.UserCourseRepository;
import com.focados.foca.modules.courses.domain.dtos.mappers.UserCourseMapper;
import com.focados.foca.modules.courses.domain.dtos.request.UpdateCourseDto;
import com.focados.foca.modules.courses.domain.dtos.request.UpdateUserCourseDto;
import com.focados.foca.modules.courses.domain.dtos.response.UserCourseResponseDto;
import com.focados.foca.modules.users.database.entity.UserModel;
import com.focados.foca.modules.users.database.repository.UserRepository;
import com.focados.foca.modules.users.domain.services.AuthUtil;
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

    public UserCourseModel createUserCourseLink(UserModel user, CourseModel course, String shareCode) {
        boolean isOwner = user.getId().equals(course.getCreatedBy().getId());
        UserCourseModel userCourse = new UserCourseModel();
        userCourse.setUser(user);
        userCourse.setCourseTemplate(course);
        userCourse.setRole(isOwner ? "owner" : "member");
        userCourse.setAccepted(true);
        userCourse.setCustomStart(course.getStartDate());
        userCourse.setCustomEnd(course.getEndDate());
        userCourse.setShareCode(shareCode);
        return userCourseRepository.save(userCourse);
    }

    public List<UserCourseResponseDto> findAllByUser() {
        UUID userId = AuthUtil.getAuthenticatedUserId();
        List<UserCourseModel> userCourses = userCourseRepository.findByUserId(userId);
        return userCourses.stream().map(UserCourseMapper::toResponse).toList();
    }

    public UserCourseResponseDto getById(UUID id) {
        UserCourseModel userCourse = userCourseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vínculo não encontrado"));
        return UserCourseMapper.toResponse(userCourse);
    }

    public UserCourseResponseDto updateUserCourse(UUID id, UpdateUserCourseDto dto) {
        UserCourseModel userCourse = userCourseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vínculo não encontrado"));
        CourseModel course = userCourse.getCourseTemplate();

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
                .orElseThrow(() -> new IllegalArgumentException("Vínculo não encontrado"));
        if (!userCourse.getUser().getId().equals(userId)) {
            throw new SecurityException("Somente o próprio usuário pode deletar seu vínculo.");
        }
        userCourseRepository.delete(userCourse);
    }

    public UserCourseResponseDto joinCourseByShareCode(String shareCode) {
        UUID userId = AuthUtil.getAuthenticatedUserId();
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        // Ache o vínculo do owner a partir do código
        UserCourseModel ownerCourse = userCourseRepository.findByShareCode(shareCode)
                .orElseThrow(() -> new IllegalArgumentException("Código inválido"));

        // Validar se já existe vínculo desse usuário para este template
        boolean alreadyMember = userCourseRepository.existsByUserIdAndCourseTemplateId(
                userId, ownerCourse.getCourseTemplate().getId());
        if (alreadyMember) throw new IllegalArgumentException("Você já participa deste curso.");
        UserCourseModel newUserCourse = createUserCourseLink(user, ownerCourse.getCourseTemplate(), shareCode);
        return UserCourseMapper.toResponse(newUserCourse);
    }

    public String generateShareCode(String name) {
        String sigla = name.replaceAll("[^A-Z]", " ").replaceAll("\\s+", "") // Letras maiúsculas
                .toUpperCase();
        if (sigla.isEmpty()) {
            sigla = name.substring(0, Math.min(3, name.length())).toUpperCase();
        }
        String randomSuffix;
        do {
            randomSuffix = java.util.UUID.randomUUID().toString()
                    .replaceAll("-", "")
                    .substring(0, 6)
                    .toUpperCase();
        } while (userCourseRepository.existsByShareCode(sigla + "-" + randomSuffix));
        return sigla + "-" + randomSuffix;
    }

}