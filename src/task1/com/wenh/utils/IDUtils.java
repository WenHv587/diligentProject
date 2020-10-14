package task1.com.wenh.utils;

import java.util.UUID;

/**
 * @author LWenH
 * @create 2020/9/28 - 23:02
 * <p>
 * 生成随机id的工具类
 */
public class IDUtils {
    /**
     * 利用UUID返回一个随机的不重复的id
     *
     * @return 返回一个5位的随机id
     */
    public static String randomId() {
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString();
        s = s.replace("-", "").toUpperCase();
        return s.substring(0, 5);
    }
}
