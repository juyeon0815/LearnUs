package com.ssafy.backend.service;

import com.ssafy.backend.dto.Attendance;
import com.ssafy.backend.dto.Award;

import java.util.List;
import java.util.Map;

public interface AwardService {
    void insert(int broadcastId, List<Attendance> chatKingList, List<Attendance> quizKingList);
    Map<String, List<Award>> getAward();
}
