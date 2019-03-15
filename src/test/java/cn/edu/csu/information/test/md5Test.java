package cn.edu.csu.information.test;

import cn.edu.csu.information.InformationApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.DigestUtils;

@Slf4j
public class md5Test extends InformationApplicationTests {
    @Test
    public void md5(){
        log.info(DigestUtils.md5DigestAsHex("123456".getBytes()));
        log.info(DigestUtils.md5DigestAsHex("123456".getBytes()));
    }

}
