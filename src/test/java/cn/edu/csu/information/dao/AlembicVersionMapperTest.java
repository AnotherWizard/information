package cn.edu.csu.information.dao;

import cn.edu.csu.information.InformationApplicationTests;
import cn.edu.csu.information.dataObject.AlembicVersion;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
public class AlembicVersionMapperTest  extends InformationApplicationTests {

    @Resource
    private AlembicVersionRepository repository;

    @Test
    public void test1(){
//         Assert.assertNotNull(repository.findAll());
        AlembicVersion alembicVersion = repository.findById("123").get();
        System.out.println(alembicVersion.getVersionNum());
    }

}