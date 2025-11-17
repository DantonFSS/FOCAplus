package com.focados.foca.modules.selfEvaluation.database.repository;

import com.focados.foca.modules.selfEvaluation.database.entity.SelfEvalAnswerModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SelfEvalAnswerRepository extends JpaRepository<SelfEvalAnswerModel, UUID> {
    List<SelfEvalAnswerModel> findAllBySessionId(UUID sessionId);
    List<SelfEvalAnswerModel> findAllByQuestionId(UUID questionId);
}