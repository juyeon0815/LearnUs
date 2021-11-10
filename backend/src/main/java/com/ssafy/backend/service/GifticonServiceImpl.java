package com.ssafy.backend.service;

import com.ssafy.backend.dao.AttendanceDao;
import com.ssafy.backend.dao.BroadcastDao;
import com.ssafy.backend.dao.GifticonDao;
import com.ssafy.backend.dao.UserDao;
import com.ssafy.backend.dto.*;
import com.ssafy.backend.dto.info.GifticonInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GifticonServiceImpl implements GifticonService{

    @Autowired
    private BroadcastDao broadcastDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AttendanceDao attendanceDao;
    @Autowired
    private GifticonDao gifticonDao;

    @Override
    public boolean insert(GifticonInfo gifticonInfo) {
        Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(gifticonInfo.getBroadcastId());
        User user = userDao.findUserByUserId(Integer.parseInt(gifticonInfo.getUserId()));
        Attendance attendance = attendanceDao.findAttendanceByBroadcastAndUser(broadcast, user);

        if (attendance == null) return false;

        Gifticon gifticon = Gifticon.builder().user(user).broadcast(broadcast).build();
        gifticonDao.save(gifticon);
        return true;
    }

    @Override
    public boolean update(GifticonInfo gifticonInfo) {
        Gifticon gifticon = gifticonDao.findGifticonByGifticonId(gifticonInfo.getGifticonId());
        Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(gifticonInfo.getBroadcastId());
        User user = userDao.findUserByUserId(Integer.parseInt(gifticonInfo.getUserId()));
        Attendance attendance = attendanceDao.findAttendanceByBroadcastAndUser(broadcast, user);

        if (attendance == null) return false;

        gifticon.setUser(user);
        gifticon.setBroadcast(broadcast);
        gifticonDao.save(gifticon);
        return true;
    }

    @Override
    public void delete(int gifticonid) {
        Gifticon gifticon = gifticonDao.findGifticonByGifticonId(gifticonid);
        gifticonDao.delete(gifticon);
    }

    @Override
    public List<Gifticon> getGifticonAll(int broadcastId) {
        Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastId);
        List<Gifticon> gifticonList = gifticonDao.findGifticonsByBroadcast(broadcast);
        return gifticonList;
    }
}
