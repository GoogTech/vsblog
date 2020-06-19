package run.vue.iblog.util;

import org.apache.shiro.SecurityUtils;
import run.vue.iblog.shiro.AccountProfile;

/**
 * Shiro工具类
 */
public class ShiroUtils {

    /**
     * 获取当前登录用户的信息
     * @return
     */
    public static AccountProfile getProfile(){
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}
