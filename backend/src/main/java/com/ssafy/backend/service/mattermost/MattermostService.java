package com.ssafy.backend.service.mattermost;

import com.ssafy.backend.dto.info.MattermostInfo;

import java.util.List;

public interface MattermostService {
    public List<Integer> selectOrdinalNo();
    public void insert(MattermostInfo mattermostInfo);
    public void update(MattermostInfo mattermostInfo);
    public boolean delete(int mattermostId);
    public List<MattermostInfo> getMattermostAll();
}
