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
public class BroadcastTrack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int broadcastTrackId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "broadcastId")
    private Broadcast broadcast;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trackId")
    private Track track;
}
