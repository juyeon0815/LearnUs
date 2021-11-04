package com.ssafy.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BroadcastReplayInfo {
    private int broadcastReplyId;
    private int broadcastId;
    private String replayUrl;
    private String openYn;
    private Map<String, String> textbook;
    private Broadcast broadcast;
}
