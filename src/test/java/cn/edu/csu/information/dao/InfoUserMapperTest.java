package cn.edu.csu.information.dao;

import cn.edu.csu.information.InformationApplicationTests;
import cn.edu.csu.information.dataObject.InfoUser;
import cn.edu.csu.information.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
public class InfoUserMapperTest extends InformationApplicationTests {
    @Resource
    private InfoUserRepository repository;

    @Test
    public void insertUser() {

      Assert.assertNotNull( repository.findAll());
    }

    @Test
    public void beforeTimeTest(){
      List<InfoUser> result= repository.findByIsAdminAndLastLoginBetween(false, DateUtil.getTimeBeforeAMonth(new Date()),new Date());
      log.info(result.toString());
      Assert.assertNotNull(result);
    }


}