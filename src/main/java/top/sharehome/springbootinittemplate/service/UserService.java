package top.sharehome.springbootinittemplate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.sharehome.springbootinittemplate.model.entity.User;

/**
 * 用户服务接口
 *
 * @author AntonyCheng
 */
public interface UserService extends IService<User> {
    /**
     * 更新头像
     *
     * @param url 新头像地址
     */
    void updateAvatar(String url);

    /**
     * 修改账号
     *
     * @param newAccount 新账号
     */
    void updateAccount(String newAccount);

    /**
     * 修改密码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void updatePassword(String oldPassword, String newPassword);
}
