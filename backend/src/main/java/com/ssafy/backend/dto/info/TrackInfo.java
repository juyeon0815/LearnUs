package com.ssafy.backend.dto.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackInfo {
    private int trackId;
    private int subjectId;
    private String trackName;
}
