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
public class BroadcastReplayOrdinal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int broadcastReplayOrdinalId;

    private int ordinalNo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "broadcastReplayId")
    private BroadcastReplay broadcastReplay;
}
