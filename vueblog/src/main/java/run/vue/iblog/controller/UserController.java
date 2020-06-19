package run.vue.iblog.controller;


import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import run.vue.iblog.common.Result;
import run.vue.iblog.entity.User;
import run.vue.iblog.service.UserService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author anonymous
 * @since 2020-06-14
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequiresAuthentication //登录权限验证:即登录之后才可以访问该接口
    @GetMapping("/index")
    public Object index(){
        User user = userService.getById(1L);
        return Result.success(user);
    }

    @GetMapping("/error")
    public Object error(){
        return Result.error("操作失败");
    }


    @PostMapping("/save")
    public Result save(@Validated @RequestBody User user){
        return Result.success(user);

    }

}
