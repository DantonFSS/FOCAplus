package com.focados.foca.modules.score.infra.http.controller;

import com.focados.foca.modules.score.domain.dtos.response.ScoreRecordResponseDTO;
import com.focados.foca.modules.score.domain.services.ScoreRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/score-records")
@RequiredArgsConstructor
public class ScoreRecordController {

    private final ScoreRecordService service;

    @GetMapping("/me")
    public List<ScoreRecordResponseDTO> getMyScores() {
        return service.findByUserId();
    }

    @GetMapping("/by-discipline/{disciplineInstanceId}")
    public List<ScoreRecordResponseDTO> getByDiscipline(@PathVariable UUID disciplineInstanceId) {
        return service.findByDisciplineInstanceId(disciplineInstanceId);
    }

    @GetMapping("/by-course/{userCourseId}")
    public List<ScoreRecordResponseDTO> getByUserCourse(@PathVariable UUID userCourseId) {
        return service.findByUserCourseId(userCourseId);
    }
}