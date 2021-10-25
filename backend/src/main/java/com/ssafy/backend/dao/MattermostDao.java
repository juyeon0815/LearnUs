package com.ssafy.backend.dao;

import com.ssafy.backend.dto.Mattermost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MattermostDao extends JpaRepository<Mattermost, String> {
    Mattermost findMattermostByMattermostId(int mattermostId);
}
