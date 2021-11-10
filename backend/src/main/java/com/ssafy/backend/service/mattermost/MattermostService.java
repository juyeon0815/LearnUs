package com.ssafy.backend.service.mattermost;

import com.ssafy.backend.dto.info.MattermostInfo;

import java.util.List;

public interface MattermostService {
    List<Integer> selectOrdinalNo();
    void insert(MattermostInfo mattermostInfo);
    void update(MattermostInfo mattermostInfo);
    boolean delete(int mattermostId);
    List<MattermostInfo> getMattermostAll();
}
