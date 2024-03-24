package top.sharehome.springbootinittemplate.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.sharehome.springbootinittemplate.common.base.R;
import top.sharehome.springbootinittemplate.common.base.ReturnCode;
import top.sharehome.springbootinittemplate.common.validate.PutGroup;
import top.sharehome.springbootinittemplate.exception.customize.CustomizeReturnException;
import top.sharehome.springbootinittemplate.model.dto.SingleFileDto;
import top.sharehome.springbootinittemplate.service.UserService;
import top.sharehome.springbootinittemplate.utils.oss.tencent.TencentUtils;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
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
        String url = TencentUtils.upload(realFile, "ai_bi/avatar");
        userService.updateAvatar(url);
        return R.ok(url);
    }

}