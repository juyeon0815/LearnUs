package com.ssafy.backend.dto.info;

import com.ssafy.backend.dto.Quiz;
import com.ssafy.backend.dto.QuizAnswer;
import com.ssafy.backend.dto.QuizSelect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizRankInfo {
    private List<QuizAnswer> quizAnswerList;
    private Map<Object, Integer> rateMap;
    private Quiz quiz;
    private List<QuizSelect> quizSelectList;
}
