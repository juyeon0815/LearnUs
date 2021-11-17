package com.ssafy.backend.dao;

import com.ssafy.backend.dto.Award;
import com.ssafy.backend.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AwardDao extends JpaRepository<Award, String> {
    Award findAwardByUserAndTypeAndDate(User user, int type, LocalDate localDate);
    @Query(nativeQuery = true, value = "select * from award where date = :date and type = :type LIMIT 10")
    List<Award> findChatAwards(LocalDate date, int type);
}
