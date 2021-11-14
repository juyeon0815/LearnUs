package com.ssafy.backend.dto.info;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.backend.dto.Track;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BroadcastInfo {
    private int broadcastId;
    private String streamingKey;
    private String thumbnailUrl;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime broadcastDate;
    private String title;
    private String teacher;
    private String description;
    private String liveCode;
    private int chatCount;
    private Map<String, String> textbook;
    private List<Track> trackList;
}
