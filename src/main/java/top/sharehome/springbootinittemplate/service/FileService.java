package top.sharehome.springbootinittemplate.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.sharehome.springbootinittemplate.model.dto.file.FilePageDto;
import top.sharehome.springbootinittemplate.model.entity.File;
import com.baomidou.mybatisplus.extension.service.IService;
import top.sharehome.springbootinittemplate.model.page.PageModel;
import top.sharehome.springbootinittemplate.model.vo.file.FileExportVo;
import top.sharehome.springbootinittemplate.model.vo.file.FilePageVo;

import java.util.List;

/**
 * 文件服务接口
 *
 * @author AntonyCheng
 */
public interface FileService extends IService<File> {

    /**
     * 管理员分页查询文件信息
     *
     * @param filePageDto 文件查询条件
     * @param pageModel   分页模型
     * @return 分页查询结果
     */
    Page<FilePageVo> pageFile(FilePageDto filePageDto, PageModel pageModel);

    /**
     * 导出文件信息表格
     *
     * @return 导出表格
     */
    List<FileExportVo> exportExcelList();

}
