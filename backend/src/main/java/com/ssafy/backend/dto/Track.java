package com.ssafy.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int trackId;

    private String name;
    private String subject;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trackSettingId")
    private TrackSetting trackSetting;
}
