package com.focados.foca.modules.selfEvaluation.database.entity;

import com.focados.foca.modules.studySessions.database.entity.StudySessionModel;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.UUID;

@Entity
@Table(name = "self_eval_answers")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SelfEvalAnswerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    private StudySessionModel session;

    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private SelfEvalQuestionModel question;

    @Column(nullable = false)
    @Min(1)
    @Max(10)
    private Integer score;
}