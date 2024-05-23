package top.sharehome.springbootinittemplate.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.sharehome.springbootinittemplate.model.dto.chart.ChartGenDto;
import top.sharehome.springbootinittemplate.model.dto.chart.ChartPageDto;
import top.sharehome.springbootinittemplate.model.entity.Chart;
import top.sharehome.springbootinittemplate.model.vo.chart.ChartAdminExportVo;
import top.sharehome.springbootinittemplate.model.vo.chart.ChartAdminPageVo;
import top.sharehome.springbootinittemplate.model.vo.chart.ChartGenVo;
import top.sharehome.springbootinittemplate.model.vo.chart.ChartUserPageVo;

import java.util.List;

/**
 * 图表服务接口
 *
 * @author AntonyCheng
 */
public interface ChartService extends IService<Chart> {

    /**
     * 管理员分页查询图表
     *
     * @param chartPageDto 图表分页Dto类
     * @return 返回分页结果
     */
    Page<ChartAdminPageVo> pageAdminChart(ChartPageDto chartPageDto);

    /**
     * 用户分页查询个人图表
     *
     * @param chartPageDto 图表分页Dto类
     * @return 返回分页结果
     */
    Page<ChartUserPageVo> pageUserChart(ChartPageDto chartPageDto);

    /**
     * 通过AI生成图表
     */
    ChartGenVo genChartByAi(ChartGenDto chartGenDto);

    /**
     * 管理员根据ID删除图表
     *
     * @param id 被删除用户的ID
     */
    void deleteChart(Long id);

    /**
     * 用户根据ID删除图表
     *
     * @param id 被删除用户的ID
     */
    void deleteUserChart(Long id);

    /**
     * 导出图表表格
     *
     * @return 导出表格
     */
    List<ChartAdminExportVo> exportExcelList();
}
