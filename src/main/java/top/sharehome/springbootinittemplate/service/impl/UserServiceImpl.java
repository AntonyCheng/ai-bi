package top.sharehome.springbootinittemplate.service.impl;

import cn.dev33.satoken.context.SaHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.sharehome.springbootinittemplate.common.base.Constants;
import top.sharehome.springbootinittemplate.common.base.ReturnCode;
import top.sharehome.springbootinittemplate.exception.customize.CustomizeReturnException;
import top.sharehome.springbootinittemplate.exception.customize.CustomizeTransactionException;
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
    @Transactional(rollbackFor = CustomizeTransactionException.class)
    public void updateAvatar(String url) {
        LambdaUpdateWrapper<User> userLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        userLambdaUpdateWrapper.set(StringUtils.isNotEmpty(url), User::getAvatar, url);
        userLambdaUpdateWrapper.eq(User::getId, LoginUtils.getLoginUserId());
        int updateResult = userMapper.update(userLambdaUpdateWrapper);
        if (updateResult == 0) {
            throw new CustomizeReturnException(ReturnCode.ERRORS_OCCURRED_IN_THE_DATABASE_SERVICE);
        }
        AuthLoginVo loginUser = (AuthLoginVo) SaHolder.getStorage().get(Constants.LOGIN_USER_KEY);
        String avatar = loginUser.getAvatar();
        if (StringUtils.isNotEmpty(avatar)) {
            TencentUtils.delete(avatar);
        }
        LoginUtils.syncLoginUser();
    }

    @Override
    @Transactional(rollbackFor = CustomizeTransactionException.class)
    public void updateAccount(String newAccount) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getAccount, newAccount);
        userLambdaQueryWrapper.ne(User::getId, LoginUtils.getLoginUserId());
        if (userMapper.exists(userLambdaQueryWrapper)) {
            throw new CustomizeReturnException(ReturnCode.USERNAME_ALREADY_EXISTS);
        }
        LambdaUpdateWrapper<User> userLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        userLambdaUpdateWrapper.set(User::getAccount, newAccount);
        userLambdaUpdateWrapper.eq(User::getId, LoginUtils.getLoginUserId());
        int updateResult = userMapper.update(userLambdaUpdateWrapper);
        if (updateResult == 0) {
            throw new CustomizeReturnException(ReturnCode.ERRORS_OCCURRED_IN_THE_DATABASE_SERVICE);
        }
        LoginUtils.syncLoginUser();
    }

    @Override
    @Transactional(rollbackFor = CustomizeTransactionException.class)
    public void updatePassword(String oldPassword, String newPassword) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        Long userId = LoginUtils.getLoginUserId();
        userLambdaQueryWrapper.eq(User::getId, userId);
        userLambdaQueryWrapper.eq(User::getPassword, oldPassword);
        User userInResult = userMapper.selectOne(userLambdaQueryWrapper);
        if (ObjectUtils.isEmpty(userInResult)) {
            throw new CustomizeReturnException(ReturnCode.PASSWORD_VERIFICATION_FAILED);
        }
        userInResult.setPassword(newPassword);
        int updateResult = userMapper.updateById(userInResult);
        if (updateResult == 0) {
            throw new CustomizeReturnException(ReturnCode.ERRORS_OCCURRED_IN_THE_DATABASE_SERVICE);
        }
        LoginUtils.logout();
    }
}
