package com.ssafy.backend.dao;

import com.ssafy.backend.dto.Broadcast;
import com.ssafy.backend.dto.Textbook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TextbookDao extends JpaRepository<Textbook, String> {
    List<Textbook> findTextbooksByBroadcast(Broadcast broadcast);
}
