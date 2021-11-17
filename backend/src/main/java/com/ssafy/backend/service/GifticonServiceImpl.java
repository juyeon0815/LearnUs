package com.ssafy.backend.service;

import com.ssafy.backend.dao.*;
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
        try {
            Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(gifticonInfo.getBroadcastId());
            User user = userDao.findUserByUserId(Integer.parseInt(gifticonInfo.getUserId()));
            Attendance attendance = attendanceDao.findAttendanceByBroadcastAndUser(broadcast, user);
            attendance.setGifticonYn("Y");
            attendanceDao.save(attendance);

            Gifticon gifticon = Gifticon.builder().user(user).broadcast(broadcast).build();
            gifticonDao.save(gifticon);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(GifticonInfo gifticonInfo) {
        try {
            Gifticon gifticon = gifticonDao.findGifticonByGifticonId(gifticonInfo.getGifticonId());
            Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(gifticonInfo.getBroadcastId());
            User user = userDao.findUserByUserId(Integer.parseInt(gifticonInfo.getUserId()));

            gifticon.setUser(user);
            gifticon.setBroadcast(broadcast);
            gifticonDao.save(gifticon);

            Attendance attendance = attendanceDao.findAttendanceByBroadcastAndUser(broadcast, user);
            String gifticonYn = gifticonDao.findGifticonByUserAndBroadcast(user, broadcast)==null?"N":"Y";
            attendance.setGifticonYn(gifticonYn);
            attendanceDao.save(attendance);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(int gifticonid) {
        try {
            Gifticon gifticon = gifticonDao.findGifticonByGifticonId(gifticonid);
            User user = gifticon.getUser();
            Broadcast broadcast = gifticon.getBroadcast();

            Attendance attendance = attendanceDao.findAttendanceByBroadcastAndUser(broadcast, user);
            attendance.setGifticonYn("N");

            attendanceDao.save(attendance);
            gifticonDao.delete(gifticon);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Gifticon> getGifticonAll(int broadcastId) {
        try {
            Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastId);
            List<Gifticon> gifticonList = gifticonDao.findGifticonsByBroadcast(broadcast);
            return gifticonList;
        } catch (Exception e) {
            return null;
        }
    }
}
