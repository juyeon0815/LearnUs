package com.ssafy.backend.service;

import com.ssafy.backend.dto.Gifticon;
import com.ssafy.backend.dto.info.GifticonInfo;

import java.util.List;

public interface GifticonService {
    public boolean insert(GifticonInfo gifticonInfo);
    public boolean update(GifticonInfo gifticonInfo);
    public void delete(int gifticonid);
    public List<Gifticon> getGifticonAll(int broadcastId);
}
