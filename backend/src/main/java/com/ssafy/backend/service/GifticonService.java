package com.ssafy.backend.service;

import com.ssafy.backend.dto.Gifticon;
import com.ssafy.backend.dto.info.GifticonInfo;

import java.util.List;

public interface GifticonService {
    boolean insert(GifticonInfo gifticonInfo);
    boolean update(GifticonInfo gifticonInfo);
    boolean delete(int gifticonid);
    List<Gifticon> getGifticonAll(int broadcastId);
}
