package run.vue.iblog.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户登录信息
 */
@Data
public class LoginDto implements Serializable {

    @NotBlank(message = "昵称不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

}
