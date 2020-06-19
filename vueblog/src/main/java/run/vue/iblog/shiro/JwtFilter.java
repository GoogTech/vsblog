package run.vue.iblog.shiro;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import run.vue.iblog.common.Result;
import run.vue.iblog.util.JwtUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * JWT过滤器
 */
@Component
public class JwtFilter extends AuthenticatingFilter {

    @Autowired
    JwtUtils jwtUtils;

    /**
     * 生成token
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = httpServletRequest.getHeader("Authorization");
        return jwt.isEmpty()?null:new JwtToken(jwt);
    }

    /**
     *
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = httpServletRequest.getHeader("Authorization");
        if(StringUtils.isEmpty(jwt)){
            return true;
        }else{
            // 校验jwt
            Claims claims = jwtUtils.getClaimByToken(jwt);
            if (claims==null || jwtUtils.isTokenExpired(claims.getExpiration())){ //token是否为空或过期
                throw new ExpiredCredentialsException("Token is invalid please login again");
            }
            // 执行登录
            return executeLogin(servletRequest,servletResponse);
        }
    }


    /**
     * 捕获登录异常
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e,
                                     ServletRequest request, ServletResponse response) {

        HttpServletResponse httpServletRequest  = (HttpServletResponse) response;

        Throwable throwable = e.getCause() == null ? e : e.getCause();
        Result errorResult = Result.error(throwable.getMessage());

        // 将错误信息封装为json数据后返回给前端
        String errorResultJson = JSONUtil.toJsonStr(errorResult);
        try {
            httpServletRequest.getWriter().println(errorResultJson);
        } catch (IOException ex) {
            //ex.printStackTrace();
        }
        return false;
    }

    /**
     * 对跨域提供支持
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个OPTIONS请求,这里我们给OPTIONS请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(org.springframework.http.HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
