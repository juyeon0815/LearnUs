package com.ssafy.backend.service;

import com.ssafy.backend.dto.Attendance;
import com.ssafy.backend.dto.Award;

import java.util.List;

public interface AwardService {
    void insert(int broadcastId, List<Attendance> chatKingList, List<Attendance> quizKingList);
    List<Award> getAward();
}
