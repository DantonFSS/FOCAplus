package com.focados.foca.modules.tasks.domain.dtos.response;

import com.focados.foca.modules.score.domain.dtos.response.ScoreRecordResponseDTO;
import com.focados.foca.modules.tasks.domain.dtos.response.TaskResponseDTO;

public record CompleteTaskResponseDTO(
        TaskResponseDTO task,
        ScoreRecordResponseDTO scoreRecord
) {}