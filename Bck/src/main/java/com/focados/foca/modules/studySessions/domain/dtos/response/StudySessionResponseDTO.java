package com.focados.foca.modules.studySessions.domain.dtos.response;

import com.focados.foca.modules.studySessions.database.entity.enums.StudySessionMode;
import com.focados.foca.modules.studySessions.database.entity.enums.StudySessionType;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class StudySessionResponseDTO {
    private UUID id;
    private StudySessionType sessionType;
    private StudySessionMode mode;
    private Integer durationSeconds;
    private Integer pomodoroCycles;
    private Integer pointsEarned;
    private ZonedDateTime startedAt;
    private ZonedDateTime endedAt;
    private UUID userId;
    private UUID userCourseId;
    private UUID disciplineInstanceId;
}