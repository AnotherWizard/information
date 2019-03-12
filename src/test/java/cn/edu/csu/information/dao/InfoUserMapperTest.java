package cn.edu.csu.information.dao;

import cn.edu.csu.information.InformationApplicationTests;
import cn.edu.csu.information.dataObject.InfoUser;
import org.junit.Test;

import javax.annotation.Resource;

public class InfoUserMapperTest extends InformationApplicationTests {
    @Resource
    private InfoUserRepository infoUserMapper;

    @Test
    public void insertUser() {
        InfoUser user = new InfoUser();
        infoUserMapper.insert(user);
    }

}