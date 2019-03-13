package cn.edu.csu.information.dao;

import cn.edu.csu.information.dataObject.InfoUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InfoUserRepository  extends JpaRepository<InfoUser,Integer> {

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


}