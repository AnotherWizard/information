package cn.edu.csu.information.sal;

import cn.edu.csu.information.InformationApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

@Slf4j
public class ImageStorageTest extends InformationApplicationTests {

    @Resource
    private ImageStorage imageStorage;

    @Test
    public void storage() {
//        log.info(imageStorage.storage());
    }
}