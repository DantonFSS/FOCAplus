package com.focados.foca.modules.courses.domain.services;

import com.focados.foca.modules.courses.database.entity.CourseModel;
import com.focados.foca.modules.courses.database.entity.UserCourseModel;
import com.focados.foca.modules.courses.database.repository.CourseRepository;
import com.focados.foca.modules.courses.database.repository.UserCourseRepository;
import com.focados.foca.modules.courses.domain.dtos.mappers.CourseMapper;
import com.focados.foca.modules.courses.domain.dtos.response.CourseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetCoursesService {
    private final UserCourseRepository userCourseRepository;

    //public List<CourseResponseDto> execute(UUID userId) {
        // Busca cursos do usuário
        //List<UserCourseModel> courses = userCourseRepository.findByUserId(userId);

        // Retorna lista de responses
        //return CourseMapper.toResponseList(courses);
   // }
}

