package top.sharehome.springbootinittemplate.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.sharehome.springbootinittemplate.common.base.R;
import top.sharehome.springbootinittemplate.common.base.ReturnCode;
import top.sharehome.springbootinittemplate.common.validate.PutGroup;
import top.sharehome.springbootinittemplate.exception.customize.CustomizeReturnException;
import top.sharehome.springbootinittemplate.model.dto.SingleFileDto;
import top.sharehome.springbootinittemplate.model.dto.user.UserUpdateAccountDto;
import top.sharehome.springbootinittemplate.model.dto.user.UserUpdatePasswordDto;
import top.sharehome.springbootinittemplate.service.UserService;
import top.sharehome.springbootinittemplate.utils.oss.minio.MinioUtils;
import top.sharehome.springbootinittemplate.utils.satoken.LoginUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户控制器
 *
 * @author AntonyCheng
 */
@RestController
@RequestMapping("/user")
@SaCheckLogin
public class UserController {

    private static final List<String> SUFFIX_LIST = new ArrayList<String>() {
        {
            add("png");
            add("jpg");
        }
    };

    @Resource
    private UserService userService;

    /**
     * 修改头像
     */
    @PutMapping("/updateAvatar")
    public R<String> updateAvatar(@Validated(PutGroup.class) SingleFileDto file) {
        MultipartFile realFile = file.getFile();
        if (realFile.getSize() == 0 || realFile.getSize() > 1024 * 1024) {
            throw new CustomizeReturnException(ReturnCode.USER_UPLOADED_FILE_IS_TOO_LARGE, "文件大于1MB");
        }
        String originalName = StringUtils.isNotBlank(realFile.getOriginalFilename()) ? realFile.getOriginalFilename() : realFile.getName();
        String suffix = FilenameUtils.getExtension(originalName).toLowerCase();
        if (!SUFFIX_LIST.contains(suffix)) {
            throw new CustomizeReturnException(ReturnCode.USER_UPLOADED_FILE_TYPE_MISMATCH, "仅支持上传png和jpg格式");
        }
        String url = MinioUtils.upload(realFile, "avatar");
        userService.updateAvatar(url);
        return R.ok("修改头像成功");
    }

    /**
     * 修改账号
     */
    @PutMapping("/updateAccount")
    public R<String> updateAccount(@RequestBody @Validated(PutGroup.class) UserUpdateAccountDto userUpdateAccountDto) {
        if (userUpdateAccountDto.getNewAccount().equals(LoginUtils.getLoginUserAccount())) {
            throw new CustomizeReturnException(ReturnCode.USERNAME_ALREADY_EXISTS, "不能是当前帐号");
        }
        userService.updateAccount(userUpdateAccountDto.getNewAccount());
        return R.ok("修改账号成功");
    }

    /**
     * 修改密码
     */
    @PutMapping("/updatePassword")
    public R<String> updatePassword(@RequestBody @Validated({PutGroup.class}) UserUpdatePasswordDto userUpdatePasswordDto) {
        if (!userUpdatePasswordDto.getNewPassword().equals(userUpdatePasswordDto.getCheckNewPassword())) {
            throw new CustomizeReturnException(ReturnCode.PASSWORD_AND_SECONDARY_PASSWORD_NOT_SAME);
        }
        userService.updatePassword(userUpdatePasswordDto.getOldPassword(), userUpdatePasswordDto.getNewPassword());
        return R.ok("修改密码成功，请重新登录");
    }

}
