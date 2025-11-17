package com.focados.foca.modules.selfEvaluation.domain.services;

import com.focados.foca.modules.selfEvaluation.database.entity.SelfEvalQuestionModel;
import com.focados.foca.modules.selfEvaluation.database.repository.SelfEvalQuestionRepository;
import com.focados.foca.modules.selfEvaluation.domain.dtos.mappers.SelfEvalQuestionMapper;
import com.focados.foca.modules.selfEvaluation.domain.dtos.request.SelfEvalQuestionRequestDTO;
import com.focados.foca.modules.selfEvaluation.domain.dtos.response.SelfEvalQuestionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SelfEvalQuestionService {
    private final SelfEvalQuestionRepository repository;

    public SelfEvalQuestionResponseDTO createQuestion(SelfEvalQuestionRequestDTO dto) {
        var question = new SelfEvalQuestionModel();
        question.setText(dto.getText());
        question.setPosition(dto.getPosition());
        question = repository.save(question);
        return SelfEvalQuestionMapper.toResponse(question);
    }

    public List<SelfEvalQuestionResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(SelfEvalQuestionMapper::toResponse)
                .toList();
    }

    public SelfEvalQuestionResponseDTO getOne(UUID id) {
        var q = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pergunta não encontrada"));
        return SelfEvalQuestionMapper.toResponse(q);
    }

    public SelfEvalQuestionResponseDTO updateQuestion(UUID id, SelfEvalQuestionRequestDTO dto) {
        var q = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pergunta não encontrada"));
        if (dto.getText() != null) q.setText(dto.getText());
        if (dto.getPosition() != null) q.setPosition(dto.getPosition());
        q = repository.save(q);
        return SelfEvalQuestionMapper.toResponse(q);
    }

    public void deleteQuestion(UUID id) {
        if (!repository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pergunta não encontrada");
        repository.deleteById(id);
    }
}