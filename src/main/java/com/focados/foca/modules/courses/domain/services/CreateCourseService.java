/*package com.focados.foca.modules.courses.domain.services;

import com.focados.foca.modules.courses.database.entity.CourseModel;
import com.focados.foca.modules.courses.database.repository.CourseRepository;
import com.focados.foca.modules.courses.domain.dtos.mappers.CourseMapper;
import com.focados.foca.modules.courses.domain.dtos.request.CreateCourseDto;
import com.focados.foca.modules.courses.domain.dtos.response.CourseResponseDto;
import com.focados.foca.modules.users.database.entity.UserModel;
import com.focados.foca.modules.users.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateCourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseResponseDto execute(CreateCourseDto dto, UUID userId) {
        // Busca usuário
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        // Mapeia DTO para entidade
        CourseModel course = CourseMapper.toEntity(dto);
        course.setCreatedBy(user);

        // Salva curso
        CourseModel savedCourse = courseRepository.save(course);

        // Retorna response
        return CourseMapper.toResponse(savedCourse);
    }
}
*/
