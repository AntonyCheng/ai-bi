package top.sharehome.springbootinittemplate.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.sharehome.springbootinittemplate.model.dto.operation.OperationAddDto;
import top.sharehome.springbootinittemplate.model.dto.operation.OperationPageDto;
import top.sharehome.springbootinittemplate.model.entity.Operation;
import top.sharehome.springbootinittemplate.model.page.PageModel;
import top.sharehome.springbootinittemplate.model.vo.operation.OperationExportVo;
import top.sharehome.springbootinittemplate.model.vo.operation.OperationPageVo;

import java.util.List;

/**
 * 操作服务接口
 *
 * @author AntonyCheng
 */
public interface OperationService extends IService<Operation> {

    /**
     * 管理员分页查询操作日志信息
     *
     * @param operationPageDto 操作信息查询条件
     * @param pageModel        分页模型
     * @return 分页查询结果
     */
    Page<OperationPageVo> pageOperation(OperationPageDto operationPageDto, PageModel pageModel);

    /**
     * 添加操作日志信息
     *
     * @param operationAddDto 被添加日志信息
     */
    void addOperation(OperationAddDto operationAddDto);

    /**
     * 清空操作记录
     */
    void clearOperation();

    /**
     * 管理员导出操作日志信息表格
     *
     * @return 用户列表结果
     */
    List<OperationExportVo> exportExcelList();
}
