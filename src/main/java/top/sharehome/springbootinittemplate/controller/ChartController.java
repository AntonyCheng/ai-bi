package top.sharehome.springbootinittemplate.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.sharehome.springbootinittemplate.common.base.R;
import top.sharehome.springbootinittemplate.common.base.ReturnCode;
import top.sharehome.springbootinittemplate.common.validate.GetGroup;
import top.sharehome.springbootinittemplate.common.validate.PostGroup;
import top.sharehome.springbootinittemplate.exception.customize.CustomizeReturnException;
import top.sharehome.springbootinittemplate.model.dto.chart.ChartGenDto;
import top.sharehome.springbootinittemplate.model.dto.chart.ChartPageDto;
import top.sharehome.springbootinittemplate.model.entity.Chart;
import top.sharehome.springbootinittemplate.model.vo.chart.ChartAdminPageVo;
import top.sharehome.springbootinittemplate.model.vo.chart.ChartGenVo;
import top.sharehome.springbootinittemplate.model.vo.chart.ChartUserPageVo;
import top.sharehome.springbootinittemplate.service.ChartService;
import top.sharehome.springbootinittemplate.utils.chat.ChatUtils;
import top.sharehome.springbootinittemplate.utils.excel.ExcelUtils;
import top.sharehome.springbootinittemplate.utils.satoken.LoginUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 图表接口
 *
 * @author AntonyCheng
 */
@RestController
@RequestMapping("/chart")
@Slf4j
@SaCheckLogin
public class ChartController {

    @Resource
    private ChartService chartService;

    /**
     * 分页获取当前用户创建的资源列表
     *
     * @param chartPageDto 分页查询Dto类
     * @return 分页结果
     */
    @PostMapping("/page")
    public R<Page<ChartUserPageVo>> pageUser(@Validated(GetGroup.class) @RequestBody ChartPageDto chartPageDto) {
        Page<ChartUserPageVo> chartPage = chartService.pageUserChart(chartPageDto);
        return R.ok(chartPage);
    }

    /**
     * 智能分析
     *
     * @param chartGenDto 图标生成Dto类
     * @return 返回生成结果
     */
    @PostMapping("/gen")
    public R<ChartGenVo> genChartByAi(@Validated(PostGroup.class) ChartGenDto chartGenDto) {
        ChartGenVo biResponse =  chartService.genChartByAi(chartGenDto);
        return R.ok(biResponse);
    }

}
