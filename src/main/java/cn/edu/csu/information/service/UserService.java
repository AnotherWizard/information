package cn.edu.csu.information.service;

import cn.edu.csu.information.dataObject.*;

import java.util.Date;
import java.util.List;

/**
 * @author liu
 */
public interface UserService {
    /**
     * 根据手机号查找用户
     * @param mobile
     * @return
     */
    InfoUser findUserByMobile(String mobile);

    /**
     *
     *根据是否为管理员查找用户列表
     * @param type
     * @return
     */
    List<InfoUser> findUserByType(Boolean type);

    /**
     * 查找最近注册的用户
     * @param startTime
     * @return
     */
    List<InfoUser> findUserRegisterNearTime(Date startTime);

    /**
     * 查找在指定时间内活跃的用户
     * @param startTime
     * @param endTime
     * @return
     */
    List<InfoUser> findUserLoginBetweenTime(Date startTime,Date endTime);

    List<InfoUserCollection> findUserCollectionByUserId(Integer userId);

    List<InfoUserFans> findUserFansByFollowerId(Integer followerId);


}
