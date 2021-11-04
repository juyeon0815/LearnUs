package com.ssafy.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuizSelect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int quizSelectId;

    private int number;
    private String view;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "quizId")
    private Quiz quiz;
}
