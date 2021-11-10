package com.ssafy.backend.dto.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GifticonInfo {
    private int gifticonId;
    private int broadcastId;
    private String userId;
}
