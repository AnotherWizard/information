package cn.edu.csu.information.dao;

import cn.edu.csu.information.dataObject.InfoUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author liuchengsheng
 */
public interface InfoUserRepository  extends JpaRepository<InfoUser,Integer> {


    @Override
    Optional<InfoUser> findById(Integer integer);

    /**
     * 根据手机号查询用户
     * @param Mobile
     * @return
     */
    InfoUser findByMobile(String Mobile);

    /**
     * 根据是否为管理员查询用户列表
     * @param isAdmin
     * @return
     */
    List<InfoUser> findByIsAdmin(Boolean isAdmin);

    /**
     * 根据起始时间和结束时间查询用户数量
     * @param isAdmin
     * @param start
     * @param end
     * @return
     */
    List<InfoUser> findByIsAdminAndLastLoginBetween(Boolean isAdmin,Date start,Date end);

    /**
     * 根据注册时间查询用户
     * @param isAdmin
     * @param startTime
     * @return
     */
    List<InfoUser> findByIsAdminAndCreateTimeAfter(Boolean isAdmin,Date startTime);


}