package top.sharehome.springbootinittemplate.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.sharehome.springbootinittemplate.common.base.Constants;
import top.sharehome.springbootinittemplate.common.base.R;
import top.sharehome.springbootinittemplate.common.validate.PostGroup;
import top.sharehome.springbootinittemplate.common.validate.PutGroup;
import top.sharehome.springbootinittemplate.model.dto.admin.AdminAddUserDto;
import top.sharehome.springbootinittemplate.model.dto.admin.AdminPageUserDto;
import top.sharehome.springbootinittemplate.model.dto.admin.AdminResetPasswordDto;
import top.sharehome.springbootinittemplate.model.dto.admin.AdminUpdateInfoDto;
import top.sharehome.springbootinittemplate.model.dto.operation.OperationAddDto;
import top.sharehome.springbootinittemplate.model.page.PageModel;
import top.sharehome.springbootinittemplate.model.vo.admin.AdminExportVo;
import top.sharehome.springbootinittemplate.model.vo.admin.AdminPageUserVo;
import top.sharehome.springbootinittemplate.model.vo.auth.AuthLoginVo;
import top.sharehome.springbootinittemplate.service.AdminService;
import top.sharehome.springbootinittemplate.service.OperationService;
import top.sharehome.springbootinittemplate.utils.document.excel.ExcelUtils;
import top.sharehome.springbootinittemplate.utils.satoken.LoginUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 管理员控制器
 *
 * @author AntonyCheng
 */
@RestController
@RequestMapping("/admin")
@SaCheckLogin
@SaCheckRole(value = {Constants.ROLE_ADMIN})
public class AdminController {

    @Resource
    private AdminService adminService;

    @Resource
    private OperationService operationService;

    /**
     * 管理员分页查询用户信息
     *
     * @param adminPageUserDto 用户信息查询条件
     * @param pageModel        分页模型
     * @return 分页查询结果
     */
    @GetMapping("/page")
    public R<Page<AdminPageUserVo>> pageUser(AdminPageUserDto adminPageUserDto, PageModel pageModel) {
        Page<AdminPageUserVo> page = adminService.pageUser(adminPageUserDto, pageModel);
        AuthLoginVo loginUser = LoginUtils.getLoginUser();
        operationService.addOperation(new OperationAddDto("/admin/page", loginUser.getId(), loginUser.getAccount(), "管理员分页查询用户信息"));
        return R.ok(page);
    }

    /**
     * 管理员添加用户
     *
     * @param adminAddUserDto 被添加用户信息
     * @return 添加结果
     */
    @PostMapping("/add")
    public R<String> addUser(@RequestBody @Validated({PostGroup.class}) AdminAddUserDto adminAddUserDto) {
        adminService.addUser(adminAddUserDto);
        AuthLoginVo loginUser = LoginUtils.getLoginUser();
        operationService.addOperation(new OperationAddDto("/admin/add", loginUser.getId(), loginUser.getAccount(), "管理员添加用户" + "[account=" + adminAddUserDto.getAccount() + "]"));
        return R.ok("添加成功");
    }

    /**
     * 管理员根据ID删除用户
     *
     * @param id 被删除用户的ID
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public R<String> deleteUser(@PathVariable("id") Long id) {
        adminService.deleteUser(id);
        AuthLoginVo loginUser = LoginUtils.getLoginUser();
        operationService.addOperation(new OperationAddDto("/admin/delete/" + id, loginUser.getId(), loginUser.getAccount(), "管理员删除用户" + "[id=" + id + "]"));
        return R.ok("删除成功");
    }

    /**
     * 管理员修改用户信息
     *
     * @param adminUpdateInfoDto 被修改后的用户信息
     * @return 修改结果
     */
    @PutMapping("/update/info")
    public R<String> updateInfo(@RequestBody @Validated({PutGroup.class}) AdminUpdateInfoDto adminUpdateInfoDto) {
        adminService.updateInfo(adminUpdateInfoDto);
        AuthLoginVo loginUser = LoginUtils.getLoginUser();
        operationService.addOperation(new OperationAddDto("/admin/update/info", loginUser.getId(), loginUser.getAccount(), "管理员修改用户信息" + "[id=" + adminUpdateInfoDto.getId() + "]"));
        return R.ok("修改信息成功");
    }

    /**
     * 管理员修改用户状态
     *
     * @param id 被修改用户的ID
     * @return 修改结果
     */
    @PutMapping("/update/state/{id}")
    public R<String> updateState(@PathVariable("id") Long id) {
        adminService.updateState(id);
        AuthLoginVo loginUser = LoginUtils.getLoginUser();
        operationService.addOperation(new OperationAddDto("/admin/update/state/" + id, loginUser.getId(), loginUser.getAccount(), "管理员修改用户状态" + "[id=" + id + "]"));
        return R.ok("修改状态成功");
    }

    /**
     * 管理员重置用户密码
     *
     * @param adminResetPasswordDto 被重置密码信息
     * @return 重置结果
     */
    @PutMapping("/reset/password")
    public R<String> resetPassword(@RequestBody @Validated({PutGroup.class}) AdminResetPasswordDto adminResetPasswordDto) {
        adminService.resetPassword(adminResetPasswordDto);
        AuthLoginVo loginUser = LoginUtils.getLoginUser();
        operationService.addOperation(new OperationAddDto("/admin/reset/password", loginUser.getId(), loginUser.getAccount(), "管理员重置用户密码" + "[id=" + adminResetPasswordDto.getId() + "，新密码=" + adminResetPasswordDto.getNewPassword() + "]"));
        return R.ok("重置密码成功");
    }

    /**
     * 导出用户表格
     *
     * @return 导出表格
     */
    @GetMapping("/export")
    public R<Void> exportExcel(HttpServletResponse response) {
        List<AdminExportVo> list = adminService.exportExcelList();
        ExcelUtils.exportHttpServletResponse(list, "用户表", AdminExportVo.class, response);
        AuthLoginVo loginUser = LoginUtils.getLoginUser();
        operationService.addOperation(new OperationAddDto("/admin/export", loginUser.getId(), loginUser.getAccount(), "导出用户表格"));
        return R.empty();
    }

}
