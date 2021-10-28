package com.ssafy.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
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
    private LocalDateTime broadcastDate;
    private String name;
    private String teacher;
    private String description;
    private String autoUploadYn;
    private String liveYn;
    private int chatCount;
}
