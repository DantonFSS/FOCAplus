/*package com.focados.foca.modules.courses.infra.http.controller;

import com.focados.foca.modules.courses.domain.dtos.request.CreateCourseDto;
import com.focados.foca.modules.courses.domain.dtos.response.CourseResponseDto;
import com.focados.foca.modules.courses.domain.services.CreateCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CreateCourseController {
    private final CreateCourseService createCourseService;

    @PostMapping
    public ResponseEntity<CourseResponseDto> create(
            @RequestBody CreateCourseDto request,
            @RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(createCourseService.execute(request, userId));
    }
}

*/