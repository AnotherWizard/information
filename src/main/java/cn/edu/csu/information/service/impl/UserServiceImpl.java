package cn.edu.csu.information.service.impl;

import cn.edu.csu.information.constants.AdminConstants;
import cn.edu.csu.information.dao.InfoUserCollectionRepository;
import cn.edu.csu.information.dao.InfoUserFansRepository;
import cn.edu.csu.information.dao.InfoUserRepository;
import cn.edu.csu.information.dataObject.InfoUser;
import cn.edu.csu.information.dataObject.InfoUserCollection;
import cn.edu.csu.information.dataObject.InfoUserFans;
import cn.edu.csu.information.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author liu
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private InfoUserRepository infoUserRepository;

    @Autowired
    private InfoUserCollectionRepository infoUserCollectionRepository;

    @Autowired
    private InfoUserFansRepository infoUserFansRepository;


    @Override
    public InfoUser findUserById(Integer integer) {
        return infoUserRepository.findById(integer).get();
    }

    @Override
    public InfoUser findUserByMobile(String mobile) {

        return infoUserRepository.findByMobile(mobile);
    }

    @Override
    public List<InfoUser> findUserByType(Boolean type) {
        return infoUserRepository.findByIsAdmin(type);
    }

    @Override
    public List<InfoUser> findUserRegisterNearTime(Date startTime) {
        return infoUserRepository.findByIsAdminAndCreateTimeAfter(AdminConstants.NOT_ADMIN,startTime);
    }

    @Override
    public List<InfoUser> findUserLoginBetweenTime(Date startTime, Date endTime) {
        return infoUserRepository.findByIsAdminAndLastLoginBetween(AdminConstants.NOT_ADMIN,startTime,endTime);
    }

    @Override
    public List<InfoUserCollection> findUserCollectionByUserId(Integer userId) {
        return infoUserCollectionRepository.findByUserId(userId);
    }

    @Override
    public List<InfoUserFans> findUserFansByFollowerId(Integer followerId) {
        return infoUserFansRepository.findByFollowerId(followerId);
    }

    @Override
    public List<InfoUserFans> findUserFansByFollowedId(Integer followedId) {
        return infoUserFansRepository.findByFollowedId(followedId);
    }

    @Override
    public InfoUser updatOrAddUser(InfoUser infoUser) {
        return infoUserRepository.save(infoUser);
    }
}
