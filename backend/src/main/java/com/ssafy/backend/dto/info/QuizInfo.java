package com.ssafy.backend.dto.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizInfo {
    private int quizId;
    private int broadcastId;
    private String useYn;
    private String type; // o : o,x 문제, c : 객관식, s : 주관식
    private String question;
    private String answer;
    private List<String> quizSelectList;
}
