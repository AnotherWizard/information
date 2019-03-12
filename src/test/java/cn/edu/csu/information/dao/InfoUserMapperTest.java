package cn.edu.csu.information.dao;

import cn.edu.csu.information.InformationApplicationTests;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

public class InfoUserMapperTest extends InformationApplicationTests {
    @Resource
    private InfoUserRepository repository;

    @Test
    public void insertUser() {

      Assert.assertNotNull( repository.findAll());
    }

}