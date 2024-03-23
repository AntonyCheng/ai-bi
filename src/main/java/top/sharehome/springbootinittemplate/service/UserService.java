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
     * @param url 新头像地址
     */
    void updateAvatar(String url);
}
