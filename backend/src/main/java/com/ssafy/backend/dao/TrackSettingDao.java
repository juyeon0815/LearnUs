package com.ssafy.backend.dao;

import com.ssafy.backend.dto.TrackSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackSettingDao extends JpaRepository<TrackSetting, String> {
    TrackSetting findTrackSettingBySemester(int semester);
    TrackSetting findTrackSettingByOrdinalNo(int ordinalNo);
}
