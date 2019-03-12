package cn.edu.csu.information.dao;

import cn.edu.csu.information.InformationApplicationTests;
import cn.edu.csu.information.dataObject.AlembicVersion;
import org.junit.Test;

import javax.annotation.Resource;

public class AlembicVersionMapperTest  extends InformationApplicationTests {

    @Resource
    private AlembicVersionRepository mapper;

    @Test
    public void insert(){
        AlembicVersion version = new AlembicVersion();
        version.setVersionNum("123");
        mapper.insert(version);
    }

}