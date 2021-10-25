package com.ssafy.backend.service;

import com.ssafy.backend.dao.MattermostDao;
import com.ssafy.backend.dto.Mattermost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MattermostServiceImpl implements MattermostService{

    @Autowired
    private MattermostDao mattermostDao;

    @Override
    public void insert(Mattermost mattermost) {
        mattermostDao.save(mattermost);
    }

    @Override
    public void update(Mattermost mattermost) {
        mattermostDao.save(mattermost);
    }

    @Override
    public boolean delete(int mattermostId) {
        Mattermost mattermost = mattermostDao.findMattermostByMattermostId(mattermostId);
        if (mattermost == null) return false;

        mattermostDao.delete(mattermost);
        return true;
    }

    @Override
    public List<Mattermost> getMattermostAll() {
        return mattermostDao.findAll();
    }
}
