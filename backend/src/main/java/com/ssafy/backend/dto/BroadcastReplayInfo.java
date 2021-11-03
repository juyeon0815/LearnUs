package com.ssafy.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BroadcastReplayInfo {
    private int broadcastReplayId;
    private int broadcastId;
    private String replayUrl;
    private char openYn;

    private Broadcast broadcast;

//    public void setBroadcastReplay(BroadcastReplay br){
//        this.broadcastReplayId = br.getBroadcastReplayId();
//        this.broadcastId = br.getBroadcastId();
//        this.replayUrl = br.getReplayUrl();
//        this.openYn = br.getOpenYn();
//    }

}
