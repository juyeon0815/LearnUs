package com.ssafy.backend.dto.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackSubjectInfo {
    private int trackSubjectId;
    private int ordinalNo;
    private String subjectName;
}
