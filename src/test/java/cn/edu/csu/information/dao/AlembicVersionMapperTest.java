package cn.edu.csu.information.dao;

import cn.edu.csu.information.InformationApplicationTests;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

public class AlembicVersionMapperTest  extends InformationApplicationTests {

    @Resource
    private AlembicVersionRepository repository;

    @Test
    public void findAll(){
         Assert.assertNotNull(repository.findAll());

    }

}