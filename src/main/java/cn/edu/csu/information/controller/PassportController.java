package cn.edu.csu.information.controller;

import cn.edu.csu.information.constants.AdminConstants;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author liuchengsheng
 */
@Slf4j
@Controller
@RequestMapping("/passport")
public class PassportController {

    @Autowired
    private Producer captchaProducer;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping("/image_code")
    public void imageCode(@RequestParam(value = "imageCodeId") String imageCodeId, HttpServletResponse response) throws IOException {

        response.setContentType("image/jpeg");

        //生成验证内容
        String text = captchaProducer.createText();
        log.info("图片验证码的内容:{}", text);

        //生成图片验证码
        BufferedImage image = captchaProducer.createImage(text);

        //保存验证码到redis
        redisTemplate.opsForValue().set(String.format("%s%s",AdminConstants.IMAGE_CODE_ID_PREFIX, imageCodeId), text,
                AdminConstants.IMAGE_CODE_REDIS_EXPIRES, TimeUnit.SECONDS);

        //返回验证图片
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);

    }

}
