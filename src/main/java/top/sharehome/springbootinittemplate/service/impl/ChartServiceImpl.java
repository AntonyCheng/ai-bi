package top.sharehome.springbootinittemplate.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.sharehome.springbootinittemplate.mapper.ChartMapper;
import top.sharehome.springbootinittemplate.model.dto.chart.ChartPageDto;
import top.sharehome.springbootinittemplate.model.entity.Chart;
import top.sharehome.springbootinittemplate.service.ChartService;

import javax.annotation.Resource;

/**
 * 图表服务接口实现类
 *
 * @author AntonyCheng
 */
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart> implements ChartService {

    @Resource
    private ChartMapper chartMapper;

    @Override
    public Page<Chart> pageChart(ChartPageDto chartPageDto) {
        Page<Chart> page = new Page<>(chartPageDto.getPage(), chartPageDto.getSize());
        chartMapper.selectPage(page, null);
        return page;
    }
}




