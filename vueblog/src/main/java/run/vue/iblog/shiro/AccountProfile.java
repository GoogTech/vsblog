package run.vue.iblog.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * 封装用户可公开的信息
 */
@Data
public class AccountProfile implements Serializable {

    private Long id;

    private String username;

    private String avatar;

    private String email;

}
