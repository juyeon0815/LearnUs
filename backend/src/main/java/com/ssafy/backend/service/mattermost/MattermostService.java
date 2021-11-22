package com.ssafy.backend.service.mattermost;

import com.ssafy.backend.dto.info.MattermostInfo;

import java.util.List;

public interface MattermostService {
    List<Integer> selectOrdinalNo();
    boolean insert(MattermostInfo mattermostInfo);
    boolean update(MattermostInfo mattermostInfo);
    boolean delete(int mattermostId);
    List<MattermostInfo> getMattermostAll();
}
