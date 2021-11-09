package com.ssafy.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Broadcast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int broadcastId;

    private String streamingKey;
    private String thumbnailUrl;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime broadcastDate;
    private String title;
    private String teacher;
    private String description;
    private String liveCode;
    private int chatCount;
}
