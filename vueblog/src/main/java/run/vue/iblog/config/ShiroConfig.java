package run.vue.iblog.config;


import run.vue.iblog.shiro.AccountRealm;
import run.vue.iblog.shiro.JwtFilter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.Filter;


/**
 * Shiro启用注解拦截控制器
 * refer to: https://github.com/alexxiyang/shiro-redis/
 */
@Configuration
public class ShiroConfig {


    @Autowired
    JwtFilter jwtFilter;

    /**
     * If you have created your own SessionManager or SessionsSecurityManager
     * Inject redisSessionDAO and redisCacheManager
     * which created by shiro-redis-spring-boot-starter already
     * @return
     */
    @Bean
    public SessionManager sessionManager(RedisSessionDAO redisSessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // inject redisSessionDAO
        sessionManager.setSessionDAO(redisSessionDAO);
        return sessionManager;
    }

    /**
     * If you have created your own SessionManager or SessionsSecurityManager
     * Inject redisSessionDAO and redisCacheManager
     * which created by shiro-redis-spring-boot-starter already
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(AccountRealm accountRealm,
                                                     SessionManager sessionManager,
                                                     RedisCacheManager redisCacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(accountRealm);
        // inject sessionManager
        securityManager.setSessionManager(sessionManager);
        // inject redisCacheManager
        securityManager.setCacheManager(redisCacheManager);
        return securityManager;
    }

    /**
     * Shiro过滤器链
     * @return
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        Map<String, String> filterMap = new LinkedHashMap<>();
        // JWT作为前后端分离的用户凭证
        filterMap.put("/**", "jwt"); // 主要通过注解方式校验权限, authc:shiro内置过滤器,即需用户登录
        chainDefinition.addPathDefinitions(filterMap);
        return chainDefinition;
    }

    /**
     * Shiro过滤器
     * @param securityManager
     * @param shiroFilterChainDefinition
     * @return
     */
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,
                                                         ShiroFilterChainDefinition shiroFilterChainDefinition) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        Map<String, Filter> filters = new HashMap<>();
        filters.put("jwt", jwtFilter);
        shiroFilter.setFilters(filters);

        Map<String, String> filterMap = shiroFilterChainDefinition.getFilterChainMap();

        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }

}
