package com.ssafy.backend.dao;

import com.ssafy.backend.dto.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackDao extends JpaRepository<Track, String> {
    Track findTRACKByName(String Name);
}
