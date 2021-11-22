package com.ssafy.backend.dao;

import com.ssafy.backend.dto.Mattermost;
import com.ssafy.backend.dto.TrackSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MattermostDao extends JpaRepository<Mattermost, String> {
    Mattermost findMattermostByMattermostId(int mattermostId);
    List<Mattermost> findMattermostsByTrackSetting(TrackSetting trackSetting);
}
