package com.focados.foca.modules.selfEvaluation.database.repository;

import com.focados.foca.modules.selfEvaluation.database.entity.SelfEvalQuestionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SelfEvalQuestionRepository extends JpaRepository<SelfEvalQuestionModel, UUID> {
}