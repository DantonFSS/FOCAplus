package com.focados.foca.modules.courses.infra.http.controller;

import com.focados.foca.modules.courses.domain.dtos.request.JoinCourseDto;
import com.focados.foca.modules.courses.domain.dtos.request.UpdateUserCourseDto;
import com.focados.foca.modules.courses.domain.dtos.response.UserCourseResponseDto;
import com.focados.foca.modules.courses.domain.services.UserCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user-courses")
@RequiredArgsConstructor
public class UserCourseController {

    private final UserCourseService service;

    @GetMapping
    public ResponseEntity<List<UserCourseResponseDto>> getAll() {
        return ResponseEntity.ok(service.findAllByUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserCourseResponseDto> getOne(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserCourseResponseDto> updateUserCourse(
            @PathVariable UUID id,
            @RequestBody UpdateUserCourseDto dto
    ) {
        return ResponseEntity.ok(service.updateUserCourse(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserCourse(@PathVariable UUID id) {
        service.deleteUserCourseById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/join")
    public ResponseEntity<UserCourseResponseDto> joinCourse(@RequestBody JoinCourseDto joinDto) {
        return ResponseEntity.ok(service.joinCourseByShareCode(joinDto.getShareCode()));
    }

}