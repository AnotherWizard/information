package cn.edu.csu.information.service.impl;

import cn.edu.csu.information.constants.AdminConstants;
import cn.edu.csu.information.dao.InfoNewsRepository;
import cn.edu.csu.information.dao.InfoUserCollectionRepository;
import cn.edu.csu.information.dao.InfoUserFansRepository;
import cn.edu.csu.information.dao.InfoUserRepository;
import cn.edu.csu.information.dataObject.InfoNews;
import cn.edu.csu.information.dataObject.InfoUser;
import cn.edu.csu.information.dataObject.InfoUserCollection;
import cn.edu.csu.information.dataObject.InfoUserFans;
import cn.edu.csu.information.dataObject.multiKeys.InfoUserCollectionMultiKey;
import cn.edu.csu.information.dataObject.multiKeys.InfoUserFansMultiKey;
import cn.edu.csu.information.dto.UserShowDto;
import cn.edu.csu.information.service.NewsService;
import cn.edu.csu.information.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Resource
    private NewsService newsService;
    @Autowired
    private InfoNewsRepository infoNewsRepository;

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
        return infoUserRepository.findByIsAdminAndCreateTimeAfter(AdminConstants.NOT_ADMIN, startTime);
    }

    @Override
    public List<InfoUser> findUserLoginBetweenTime(Date startTime, Date endTime) {
        return infoUserRepository.findByIsAdminAndLastLoginBetween(AdminConstants.NOT_ADMIN, startTime, endTime);
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

    @Override
    public void deleteUserCollectionById(InfoUserCollectionMultiKey infoUserCollectionMultiKey) {
        infoUserCollectionRepository.deleteById(infoUserCollectionMultiKey);
    }

    @Override
    public InfoUserCollection saveUserCollection(InfoUserCollection userCollection) {
        return infoUserCollectionRepository.saveAndFlush(userCollection);
    }

    @Override
    public InfoUserFans saveUserFans(InfoUserFans infoUserFans) {
        return infoUserFansRepository.save(infoUserFans);
    }

    @Override
    public void deleteFansById(InfoUserFansMultiKey infoUserFansMultiKey) {
        infoUserFansRepository.deleteById(infoUserFansMultiKey);
    }

    @Override
    public List<UserShowDto> findUserFollowed(Integer userId) {

        List<InfoUserFans> infoUserFans = infoUserFansRepository.findByFollowerId(userId);

        List<Integer> userFollowedIdList = new ArrayList<>();

        infoUserFans.forEach((userFans) -> {
            userFollowedIdList.add(userFans.getFollowedId());
        });

        List<InfoUser> userFollowedList = infoUserRepository.findByIdIn(userFollowedIdList);

        List<UserShowDto> userShowDtoList = new ArrayList<>();

        userFollowedList.forEach((userFollowed) -> {
            UserShowDto userShowDto = new UserShowDto();
            BeanUtils.copyProperties(userFollowed, userShowDto);
            userShowDto.setNewsCount(newsService.findNewsByUserId(userShowDto.getId()).size());
            userShowDto.setFollowersCount(findUserFansByFollowedId(userShowDto.getId()).size());
            userShowDtoList.add(userShowDto);
        });

        return userShowDtoList;
    }

    @Override
    public List<InfoNews> findUserCollection(Integer userId) {
        //得到collection表中的相关数据
        List<InfoUserCollection> collectionList = infoUserCollectionRepository.findByUserId(userId);

        List<Integer> newIdList = new ArrayList<>();
        for (InfoUserCollection collection : collectionList) {
            newIdList.add(collection.getNewsId());
        }
        return infoNewsRepository.findByIdIn(newIdList);
    }
}
