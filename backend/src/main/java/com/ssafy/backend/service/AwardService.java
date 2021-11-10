package com.ssafy.backend.service;

import com.ssafy.backend.dto.Attendance;
import com.ssafy.backend.dto.Award;

import java.util.List;

public interface AwardService {
    public void insert(List<Attendance> chatKingList, List<Attendance> quizKingList);
    public List<Award> getAward();
}
