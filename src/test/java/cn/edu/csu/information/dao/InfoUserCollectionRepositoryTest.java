package cn.edu.csu.information.dao;

import cn.edu.csu.information.InformationApplicationTests;
import cn.edu.csu.information.dataObject.InfoUserCollection;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class InfoUserCollectionRepositoryTest extends InformationApplicationTests {
    @Autowired
    private InfoUserCollectionRepository repository;

    @Test
    public void findAll() {
        Assert.assertNotNull(repository.findById(1));
    }

    @Test
    public void update() {
        InfoUserCollection collection = repository.findByUserIdAndNewsId(1, 1);
        collection.setCreateTime(new Date());
        Assert.assertNotNull(repository.save(collection));
    }

}