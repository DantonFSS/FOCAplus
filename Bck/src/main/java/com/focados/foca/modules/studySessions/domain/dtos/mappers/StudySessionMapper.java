package com.focados.foca.modules.studySessions.domain.dtos.mappers;


import com.focados.foca.modules.studySessions.database.entity.StudySessionModel;
import com.focados.foca.modules.studySessions.domain.dtos.response.StudySessionResponseDTO;

public class StudySessionMapper {
    public static StudySessionResponseDTO toResponse(StudySessionModel session) {
        StudySessionResponseDTO dto = new StudySessionResponseDTO();
        dto.setId(session.getId());
        dto.setSessionType(session.getSessionType());
        dto.setMode(session.getMode());
        dto.setDurationSeconds(session.getDurationSeconds());
        dto.setPomodoroCycles(session.getPomodoroCycles());
        dto.setPointsEarned(session.getPointsEarned());
        dto.setStartedAt(session.getStartedAt());
        dto.setEndedAt(session.getEndedAt());
        dto.setUserId(session.getUser().getId());
        dto.setUserCourseId(session.getUserCourse().getId());
        dto.setDisciplineInstanceId(session.getDisciplineInstance() == null ? null : session.getDisciplineInstance().getId());
        return dto;
    }
}