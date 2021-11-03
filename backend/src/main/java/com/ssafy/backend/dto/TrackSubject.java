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
public class TrackSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int trackSubjectId;

    private String subjectName;
    private String nowSubject;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trackSettingId")
    private TrackSetting trackSetting;
}
