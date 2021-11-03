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
public class MattermostTrack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mattermostTrackId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mattermostId")
    private Mattermost mattermost;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trackId")
    private Track track;
}
