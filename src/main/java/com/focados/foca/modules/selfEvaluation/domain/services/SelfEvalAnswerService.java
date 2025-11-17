package com.focados.foca.modules.selfEvaluation.domain.services;

import com.focados.foca.modules.selfEvaluation.database.entity.SelfEvalAnswerModel;
import com.focados.foca.modules.selfEvaluation.database.repository.SelfEvalAnswerRepository;
import com.focados.foca.modules.selfEvaluation.database.repository.SelfEvalQuestionRepository;
import com.focados.foca.modules.selfEvaluation.domain.dtos.mappers.SelfEvalAnswerMapper;
import com.focados.foca.modules.selfEvaluation.domain.dtos.request.SelfEvalAnswerRequestDTO;
import com.focados.foca.modules.selfEvaluation.domain.dtos.response.SelfEvalAnswerResponseDTO;
import com.focados.foca.modules.studySessions.database.repository.StudySessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SelfEvalAnswerService {
    private final SelfEvalAnswerRepository answerRepo;
    private final SelfEvalQuestionRepository questionRepo;
    private final StudySessionRepository sessionRepo;

    public SelfEvalAnswerResponseDTO create(SelfEvalAnswerRequestDTO dto) {
        var question = questionRepo.findById(dto.getQuestionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pergunta não encontrada"));
        var session = sessionRepo.findById(dto.getSessionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sessão não encontrada"));

        if (dto.getScore() == null || dto.getScore() < 1 || dto.getScore() > 10)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Score deve estar entre 1 e 10");

        var answer = new SelfEvalAnswerModel();
        answer.setSession(session);
        answer.setQuestion(question);
        answer.setScore(dto.getScore());
        answer = answerRepo.save(answer);
        return SelfEvalAnswerMapper.toResponse(answer);
    }

    public List<SelfEvalAnswerResponseDTO> getAll() {
        return answerRepo.findAll().stream()
                .map(SelfEvalAnswerMapper::toResponse)
                .toList();
    }

    public List<SelfEvalAnswerResponseDTO> getAllByQuestionId(UUID questionId) {
        return answerRepo.findAllByQuestionId(questionId)
                .stream()
                .map(SelfEvalAnswerMapper::toResponse)
                .toList();
    }

    public List<SelfEvalAnswerResponseDTO> getAllBySessionId(UUID sessionId) {
        return answerRepo.findAllBySessionId(sessionId)
                .stream()
                .map(SelfEvalAnswerMapper::toResponse)
                .toList();
    }

    public SelfEvalAnswerResponseDTO getOne(UUID id) {
        var answer = answerRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resposta não encontrada"));
        return SelfEvalAnswerMapper.toResponse(answer);
    }

    public SelfEvalAnswerResponseDTO update(UUID id, SelfEvalAnswerRequestDTO dto) {
        var answer = answerRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resposta não encontrada"));

        // Permita atualizar score e/ou relink question/session (se precisar no futuro)
        if (dto.getScore() != null) {
            if (dto.getScore() < 1 || dto.getScore() > 10)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Score deve estar entre 1 e 10");
            answer.setScore(dto.getScore());
        }
        if (dto.getQuestionId() != null) {
            var question = questionRepo.findById(dto.getQuestionId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pergunta não encontrada"));
            answer.setQuestion(question);
        }
        if (dto.getSessionId() != null) {
            var session = sessionRepo.findById(dto.getSessionId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sessão não encontrada"));
            answer.setSession(session);
        }
        answer = answerRepo.save(answer);
        return SelfEvalAnswerMapper.toResponse(answer);
    }

    public void delete(UUID id) {
        if (!answerRepo.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resposta não encontrada");
        answerRepo.deleteById(id);
    }
}