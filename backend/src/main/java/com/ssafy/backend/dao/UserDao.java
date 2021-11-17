package com.ssafy.backend.dao;

import com.ssafy.backend.dto.Track;
import com.ssafy.backend.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDao extends JpaRepository<User, String> {
    User findUserByEmail(String email);
    User findUserByUserId(int userId);
    User findUserByName(String name);
    List<User> findUserByTrack(Track track);
    List<User> findUsersByTrackAndStatusCode(Track track, String statusCode);
    List<User> findUserByOrdinalNoAndStatusCode(int ordinalNo, String statusCode);
    User findUserByUserIdAndPhone(int userId, String phone);
    User findUserByUserIdAndEmailAndPhone(int userId, String email, String phone);

    @Query(nativeQuery = true, value = "select region from user group by region")
    List<String> findRegions();
}
