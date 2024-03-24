package top.sharehome.springbootinittemplate.service.impl;

import cn.dev33.satoken.context.SaHolder;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.Login;
import org.springframework.stereotype.Service;
import top.sharehome.springbootinittemplate.common.base.Constants;
import top.sharehome.springbootinittemplate.common.base.ReturnCode;
import top.sharehome.springbootinittemplate.exception.customize.CustomizeReturnException;
import top.sharehome.springbootinittemplate.mapper.UserMapper;
import top.sharehome.springbootinittemplate.model.entity.User;
import top.sharehome.springbootinittemplate.model.vo.auth.AuthLoginVo;
import top.sharehome.springbootinittemplate.service.UserService;
import top.sharehome.springbootinittemplate.utils.oss.tencent.TencentUtils;
import top.sharehome.springbootinittemplate.utils.satoken.LoginUtils;

import javax.annotation.Resource;

/**
 * 用户服务实现类
 *
 * @author AntonyCheng
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public void updateAvatar(String url) {
        LambdaUpdateWrapper<User> userLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        userLambdaUpdateWrapper.set(StringUtils.isNotEmpty(url), User::getAvatar, url);
        userLambdaUpdateWrapper.eq(User::getId, LoginUtils.getLoginUserId());
        int updateResult = userMapper.update(userLambdaUpdateWrapper);
        if (updateResult == 0) {
            throw new CustomizeReturnException(ReturnCode.ERRORS_OCCURRED_IN_THE_DATABASE_SERVICE);
        }
        AuthLoginVo loginUser = (AuthLoginVo) SaHolder.getStorage().get(Constants.LOGIN_USER_KEY);
        TencentUtils.delete(loginUser.getAvatar());
        LoginUtils.syncLoginUser();
    }
}
