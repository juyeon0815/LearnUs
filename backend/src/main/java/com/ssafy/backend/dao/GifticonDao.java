package com.ssafy.backend.dao;

import com.ssafy.backend.dto.Broadcast;
import com.ssafy.backend.dto.Gifticon;
import com.ssafy.backend.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GifticonDao extends JpaRepository<Gifticon, String> {
    Gifticon findGifticonByGifticonId(int gifticonId);
    Gifticon findGifticonByUserAndBroadcast(User user, Broadcast broadcast);
    List<Gifticon> findGifticonsByBroadcast(Broadcast broadcast);
}
