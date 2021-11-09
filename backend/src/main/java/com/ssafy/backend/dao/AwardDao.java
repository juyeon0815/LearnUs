package com.ssafy.backend.dao;

import com.ssafy.backend.dto.Award;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AwardDao extends JpaRepository<Award, String> {
}
