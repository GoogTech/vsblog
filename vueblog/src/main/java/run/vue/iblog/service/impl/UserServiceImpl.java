package run.vue.iblog.service.impl;

import run.vue.iblog.entity.User;
import run.vue.iblog.mapper.UserMapper;
import run.vue.iblog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author anonymous
 * @since 2020-06-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
