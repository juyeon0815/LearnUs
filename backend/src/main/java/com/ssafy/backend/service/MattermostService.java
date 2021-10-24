package com.ssafy.backend.service;

import com.ssafy.backend.dto.Mattermost;

import java.util.List;

public interface MattermostService {
    public void insert(Mattermost mattermost);
    public void update(Mattermost mattermost);
    public boolean delete(int mattermostId);
    public List<Mattermost> getMattermostAll();
}
