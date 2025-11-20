package com.focados.foca.modules.selfEvaluation.database.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "self_eval_questions")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SelfEvalQuestionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String text;

    @Column
    private Integer position;
}