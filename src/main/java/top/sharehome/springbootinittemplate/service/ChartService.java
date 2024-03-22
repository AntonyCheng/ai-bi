package top.sharehome.springbootinittemplate.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.sharehome.springbootinittemplate.model.dto.chart.ChartPageDto;
import top.sharehome.springbootinittemplate.model.entity.Chart;

/**
 * 图表服务接口
 *
 * @author AntonyCheng
 */
public interface ChartService extends IService<Chart> {

    /**
     * 图表分页查询
     *
     * @param chartPageDto 图表分页Dto类
     * @return 返回分页结果
     */
    Page<Chart> pageChart(ChartPageDto chartPageDto);
}
