package cn.edu.csu.information.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liuchengsheng
 */
public class AdminUtil {

    private static Pattern MOBILE = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");

    public static boolean isMobile(final String str) {

        Matcher m = MOBILE.matcher(str);

        return m.matches();
    }

    public static String genSmsCode() {
        Random random = new Random();

        Integer number = random.nextInt(900000) + 100000;

        return String.valueOf(number);
    }

}
