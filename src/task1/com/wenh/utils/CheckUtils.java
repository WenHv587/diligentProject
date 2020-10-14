package task1.com.wenh.utils;

/**
 * @author LWenH
 * @create 2020/9/29 - 18:12
 * <p>
 * 对用户的输入进行校验
 */
public class CheckUtils {
    /**
     * 对用户输入的年龄进行校验
     *
     * @param age 用户输入的年龄
     * @return 返回0代表用户输入的不是数字，返回1代表用户输入的年龄不合规范，返回2代表输入正常
     */
    public static int checkAge(String age) {
        String regex = "-?[0-9]+(\\\\.[0-9]+)?";
        if (! age.matches(regex)) {
            return 0;
        }
        if (Integer.parseInt(age) < 0 || Integer.parseInt(age) > 1000) {
            return 1;
        }
        return 2;
    }

    /**
     * 对用户的输入的属性值进行非空校验
     *
     * @param property 用户输入的属性值
     * @return 为空返回false 不为空返回true
     */
    public static boolean checkProperty(String property) {
        return property != null && ! property.trim().isEmpty();
    }
}
