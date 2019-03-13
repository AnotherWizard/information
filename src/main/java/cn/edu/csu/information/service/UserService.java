package cn.edu.csu.information.service;

import cn.edu.csu.information.dataObject.InfoUser;

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
     * @param type
     * @return
     */
    List<InfoUser> findUserByType(Boolean type);


}
