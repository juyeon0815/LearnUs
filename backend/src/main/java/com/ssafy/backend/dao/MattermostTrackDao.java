package com.ssafy.backend.dao;

import com.ssafy.backend.dto.Mattermost;
import com.ssafy.backend.dto.MattermostTrack;
import com.ssafy.backend.dto.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MattermostTrackDao extends JpaRepository<MattermostTrack, String> {
    List<MattermostTrack> findMattermostTracksByMattermost(Mattermost mattermost);
    List<MattermostTrack> findMattermostTracksByTrack(Track track);
    MattermostTrack findMattermostTrackByMattermostAndTrack(Mattermost mattermost, Track track);
}
