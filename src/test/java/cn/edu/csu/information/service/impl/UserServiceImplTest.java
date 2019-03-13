package cn.edu.csu.information.service.impl;

import cn.edu.csu.information.InformationApplicationTests;
import cn.edu.csu.information.service.UserService;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

public class UserServiceImplTest  extends InformationApplicationTests {
    @Resource
    private UserService service;

    @Test
    public void findUserByMobile() {
        assertNotNull(service.findUserByMobile("17347313219"));
    }
}