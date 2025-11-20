package com.focados.foca.modules.score.domain.dtos.mappers;

import com.focados.foca.modules.score.database.entity.ScoreRecordModel;
import com.focados.foca.modules.score.domain.dtos.response.ScoreRecordResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ScoreRecordMapper {
    public ScoreRecordResponseDTO toDTO(ScoreRecordModel model) {
        if (model == null) return null;
        return new ScoreRecordResponseDTO(
                model.getId(),
                model.getUser().getId(),
                model.getUserCourse().getId(),
                model.getDisciplineInstance() != null ? model.getDisciplineInstance().getId() : null,
                model.getSourceType().name(),
                model.getSourceId(),
                model.getPoints(),
                model.getCreatedAt()
        );
    }
}