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
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int trackId;

    private String trackName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trackSubjectId")
    private TrackSubject trackSubject;
}
