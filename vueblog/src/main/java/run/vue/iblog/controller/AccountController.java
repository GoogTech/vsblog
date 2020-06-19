package run.vue.iblog.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import run.vue.iblog.common.Result;
import run.vue.iblog.dto.LoginDto;
import run.vue.iblog.entity.User;
import run.vue.iblog.service.UserService;
import run.vue.iblog.util.JwtUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录接口
 */
@RestController
public class AccountController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    /**
     * 登录
     *
     * @param loginDto
     * @param response
     * @return
     */
    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {

        //判断用户是否存在
        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        Assert.notNull(user, "账号不存在");

        //判断密码是否正确
        if (user == null || !user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
            return Result.error("密码错误");
        }

        //根据用户id生成jwt
        String jwt = jwtUtils.generateToken(user.getId());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        //返回用户信息
        return Result.success("登录成功",MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .map()
        );
    }


    /**
     * 注销
     *
     * @return
     */
    @RequiresAuthentication //登录后才能访问该接口
    @GetMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.success("注销成功");
    }
}
