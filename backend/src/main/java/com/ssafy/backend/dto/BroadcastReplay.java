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
public class BroadcastReplay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int broadcastReplayId;

    private String replayUrl;
    private String openYn;

    @OneToOne
    @JoinColumn(name = "broadcastId")
    private Broadcast broadcast;
}
