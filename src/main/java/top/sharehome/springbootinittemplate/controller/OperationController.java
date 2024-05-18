package top.sharehome.springbootinittemplate.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.sharehome.springbootinittemplate.common.base.Constants;
import top.sharehome.springbootinittemplate.common.base.R;
import top.sharehome.springbootinittemplate.model.dto.operation.OperationAddDto;
import top.sharehome.springbootinittemplate.model.dto.operation.OperationPageDto;
import top.sharehome.springbootinittemplate.model.page.PageModel;
import top.sharehome.springbootinittemplate.model.vo.auth.AuthLoginVo;
import top.sharehome.springbootinittemplate.model.vo.operation.OperationPageVo;
import top.sharehome.springbootinittemplate.service.OperationService;
import top.sharehome.springbootinittemplate.utils.satoken.LoginUtils;

import javax.annotation.Resource;

/**
 * 操作控制器
 *
 * @author AntonyCheng
 */
@RestController
@RequestMapping("/operation")
@SaCheckLogin
@SaCheckRole(value = {Constants.ROLE_ADMIN})
public class OperationController {

    @Resource
    private OperationService operationService;

    /**
     * 管理员分页查询操作日志信息
     *
     * @param operationPageDto 操作信息查询条件
     * @param pageModel        分页模型
     * @return 分页查询结果
     */
    @GetMapping("/page")
    public R<Page<OperationPageVo>> pageOperation(OperationPageDto operationPageDto, PageModel pageModel) {
        Page<OperationPageVo> page = operationService.pageOperation(operationPageDto, pageModel);
        AuthLoginVo loginUser = LoginUtils.getLoginUser();
        operationService.addOperation(new OperationAddDto("/operation/page", loginUser.getId(), loginUser.getAccount(), "管理员分页查询操作日志信息"));
        return R.ok(page);
    }

}
