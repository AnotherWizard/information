package cn.edu.csu.information.controller;

import cn.edu.csu.information.constants.AdminConstants;
import cn.edu.csu.information.dataObject.InfoUser;
import cn.edu.csu.information.enums.ResultEnum;
import cn.edu.csu.information.utils.AdminUtil;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
    public void imageCode(@RequestParam(value = "imageCodeId") String imageCodeId,
                          HttpServletResponse response) throws IOException {

        response.setContentType("image/jpeg");

        //生成验证内容
        String text = captchaProducer.createText();
        log.info("图片验证码的内容:{}", text);

        //生成图片验证码
        BufferedImage image = captchaProducer.createImage(text);

        //保存验证码到redis
        redisTemplate.opsForValue().set(String.format("%s%s", AdminConstants.IMAGE_CODE_ID_PREFIX, imageCodeId), text,
                AdminConstants.IMAGE_CODE_REDIS_EXPIRES, TimeUnit.SECONDS);

        //返回验证图片
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);

    }

    @ResponseBody
    @PostMapping("/sms_code")
    public Map newsReviewAction(@RequestBody Map map) {

        Map<String, Object> result = new HashMap<>();

        String mobile = (String) map.get("mobile");
        String imageCode = (String) map.get("image_code");
        String imageCodeId = (String) map.get("image_code_id");

        //校验手机号
        if (!AdminUtil.isMobile(mobile)) {
            result.put("errmsg", ResultEnum.MOBILEERR.getMsg());
            return result;
        }

        //取到真正的验证码
        String realImageCode = redisTemplate.opsForValue().get(String.format("%s%s",
                AdminConstants.IMAGE_CODE_ID_PREFIX, imageCodeId));

        if (StringUtils.isEmpty(realImageCode)) {
            result.put("errmsg", ResultEnum.TIMEOUTERR.getMsg());
            return result;
        }

        if (!realImageCode.toUpperCase().equals(imageCode.toUpperCase())) {
            result.put("errmsg", ResultEnum.CODEERR.getMsg());
            return result;
        }

        String smsCode = AdminUtil.genSmsCode();

        log.info("短信验证码的内容:{}", smsCode);

        //存到redis
        redisTemplate.opsForValue().set(String.format("%s%s", AdminConstants.SMS_CODE_PREFIX, mobile), smsCode,
                AdminConstants.SMS_CODE_REDIS_EXPIRES, TimeUnit.SECONDS);

        result.put("errno", ResultEnum.OK.getCode());
        result.put("errmsg", ResultEnum.OK.getMsg());
        return result;
    }

    @ResponseBody
    @PostMapping("/register")
    public Map register(@RequestBody Map map) {

        Map<String, Object> result = new HashMap<>();

        String mobile = (String) map.get("mobile");
        String smscode = (String) map.get("smscode");
        String password = (String) map.get("password");

        String realSmsCode = redisTemplate.opsForValue().get(String.format("%s%s", AdminConstants.SMS_CODE_PREFIX, mobile));

        if (StringUtils.isEmpty(realSmsCode)) {
            result.put("errmsg", ResultEnum.TIMEOUTERR.getMsg());
            return result;
        }

        if (!realSmsCode.equals(smscode)) {
            result.put("errmsg", ResultEnum.CODEERR.getMsg());
            return result;
        }

        InfoUser infoUser = new InfoUser();
        infoUser.setCreateTime(new Date());
        infoUser.setMobile(mobile);
        infoUser.setNickName(mobile);
        infoUser.setLastLogin(new Date());

        infoUser.setPasswordHash(DigestUtils.md5DigestAsHex(password.getBytes()));



        result.put("errno", ResultEnum.OK.getCode());
        result.put("errmsg", ResultEnum.OK.getMsg());
        return result;
    }

}
