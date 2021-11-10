package com.ssafy.backend.dto.info;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizAnswerInfo {
    private int quizId;
    private int userId;
    private int broadcastId;
    private String answer;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime regDate;
}
