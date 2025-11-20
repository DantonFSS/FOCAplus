package com.focados.foca.modules.courses.infra.http.controller;

import com.focados.foca.modules.courses.domain.dtos.response.CourseResponseDto;
import com.focados.foca.modules.courses.domain.services.GetCoursesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class GetCoursesController {
    private final GetCoursesService getCoursesService;

   /* @GetMapping
    public ResponseEntity<List<CourseResponseDto>> getAll(@RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(getCoursesService.execute(userId));
    }*/
}

