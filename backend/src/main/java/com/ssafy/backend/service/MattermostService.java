package com.ssafy.backend.service;

import com.ssafy.backend.dto.MattermostInfo;

import java.util.List;

public interface MattermostService {
    public void insert(MattermostInfo mattermostInfo);
    public void update(MattermostInfo mattermostInfo);
    public boolean delete(int mattermostId);
    public List<MattermostInfo> getMattermostAll();
}
