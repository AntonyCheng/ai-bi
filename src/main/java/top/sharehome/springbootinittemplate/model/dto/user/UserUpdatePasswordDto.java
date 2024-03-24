package top.sharehome.springbootinittemplate.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.sharehome.springbootinittemplate.common.validate.PostGroup;
import top.sharehome.springbootinittemplate.common.validate.PutGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static top.sharehome.springbootinittemplate.common.base.Constants.REGEX_NUMBER_AND_LETTER;

/**
 * 用户修改账号Dto类
 *
 * @author AntonyCheng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserUpdatePasswordDto implements Serializable {

    private static final long serialVersionUID = -2873349826470166944L;

    /**
     * 旧密码
     */
    @NotBlank(message = "旧密码不能为空", groups = {PutGroup.class})
    private String oldPassword;

    /**
     * 新密码
     */
    @Size(min = 5, max = 16, message = "密码长度介于5-16位之间", groups = {PutGroup.class})
    @NotBlank(message = "新密码密码不能为空", groups = {PutGroup.class})
    private String newPassword;

    /**
     * 二次输入的新密码
     */
    @NotBlank(message = "二次新密码不能为空", groups = {PutGroup.class})
    private String checkNewPassword;

}