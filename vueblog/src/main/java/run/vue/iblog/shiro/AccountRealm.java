package run.vue.iblog.shiro;

import cn.hutool.core.bean.BeanUtil;
import net.sf.jsqlparser.expression.LongValue;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import run.vue.iblog.entity.User;
import run.vue.iblog.service.UserService;
import run.vue.iblog.util.JwtUtils;

/**
 * Shiro配置信息
 */
@Component
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        // 判断是否是JWT Token
        return token instanceof JwtToken;
    }

    /**
     * 授权认证
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 身份验证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) token;

        String userId = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();
        User user = userService.getById(Long.valueOf(userId));
        if (user==null){
            throw new UnknownAccountException("account is not nonexistent");
        }else if (user.getStatus()==-1){
            throw new LockedAccountException("account is locked");

        }

        AccountProfile accountProfile = new AccountProfile();
        BeanUtil.copyProperties(user,accountProfile);

        return new  SimpleAuthenticationInfo(accountProfile,jwtToken.getCredentials(),getName());
    }
}
