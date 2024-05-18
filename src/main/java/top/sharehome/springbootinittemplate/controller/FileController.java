package top.sharehome.springbootinittemplate.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.sharehome.springbootinittemplate.common.base.Constants;
import top.sharehome.springbootinittemplate.service.FileService;

import javax.annotation.Resource;

/**
 * 文件控制器
 *
 * @author AntonyCheng
 */
@RestController
@RequestMapping("/file")
@SaCheckLogin
@SaCheckRole(value = {Constants.ROLE_ADMIN, Constants.ROLE_USER}, mode = SaMode.OR)
public class FileController {

    @Resource
    private FileService fileService;

}
