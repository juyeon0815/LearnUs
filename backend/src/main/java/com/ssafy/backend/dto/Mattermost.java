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
public class Mattermost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mattermostId;

    private String webhook;
    private String name;
    private String pathName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trackSettingId")
    TrackSetting trackSetting;
}
