package top.sharehome.springbootinittemplate.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.sharehome.springbootinittemplate.common.base.Constants;
import top.sharehome.springbootinittemplate.common.base.R;
import top.sharehome.springbootinittemplate.model.dto.file.FilePageDto;
import top.sharehome.springbootinittemplate.model.dto.operation.OperationAddDto;
import top.sharehome.springbootinittemplate.model.page.PageModel;
import top.sharehome.springbootinittemplate.model.vo.auth.AuthLoginVo;
import top.sharehome.springbootinittemplate.model.vo.file.FileExportVo;
import top.sharehome.springbootinittemplate.model.vo.file.FilePageVo;
import top.sharehome.springbootinittemplate.service.FileService;
import top.sharehome.springbootinittemplate.service.OperationService;
import top.sharehome.springbootinittemplate.utils.document.excel.ExcelUtils;
import top.sharehome.springbootinittemplate.utils.satoken.LoginUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件控制器
 *
 * @author AntonyCheng
 */
@RestController
@RequestMapping("/file")
@SaCheckLogin
@SaCheckRole(value = {Constants.ROLE_ADMIN})
public class FileController {

    @Resource
    private FileService fileService;

    @Resource
    private OperationService operationService;

    /**
     * 管理员分页查询文件信息
     *
     * @param filePageDto 文件查询条件
     * @param pageModel   分页模型
     * @return 分页查询结果
     */
    @GetMapping("/page")
    public R<Page<FilePageVo>> pageFile(FilePageDto filePageDto, PageModel pageModel) {
        Page<FilePageVo> page = fileService.pageFile(filePageDto, pageModel);
        AuthLoginVo loginUser = LoginUtils.getLoginUser();
        operationService.addOperation(new OperationAddDto("/file/page", loginUser.getId(), loginUser.getAccount(), "管理员分页查询文件信息"));
        return R.ok(page);
    }

    /**
     * 导出文件信息表格
     *
     * @return 导出表格
     */
    @GetMapping("/export")
    public R<Void> exportExcel(HttpServletResponse response) {
        List<FileExportVo> list = fileService.exportExcelList();
        ExcelUtils.exportHttpServletResponse(list, "文件信息表", FileExportVo.class, response);
        AuthLoginVo loginUser = LoginUtils.getLoginUser();
        operationService.addOperation(new OperationAddDto("/file/export", loginUser.getId(), loginUser.getAccount(), "导出文件表格"));
        return R.empty();
    }


}
