package top.sharehome.springbootinittemplate.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
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
import top.sharehome.springbootinittemplate.model.vo.chart.ChartGenVo;
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
    public R<Page<Chart>> page(@Validated(GetGroup.class) @RequestBody ChartPageDto chartPageDto) {
        chartPageDto.setUserId(LoginUtils.getLoginUserId());
        Page<Chart> chartPage = chartService.pageChart(chartPageDto);
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
        long size = chartGenDto.getFile().getSize();
        if (size > 1024 * 1024) {
            throw new CustomizeReturnException(ReturnCode.USER_UPLOADED_FILE_IS_TOO_LARGE);
        }
        String originalFilename = chartGenDto.getFile().getOriginalFilename();
        String suffix = FileUtil.getSuffix(originalFilename);
        final List<String> validFileSuffixes = Arrays.asList("xlsx", "xls");
        if (!validFileSuffixes.contains(suffix)) {
            throw new CustomizeReturnException(ReturnCode.USER_UPLOADED_FILE_TYPE_MISMATCH);
        }
        String name = chartGenDto.getName();
        String goal = chartGenDto.getGoal();
        String chartType = chartGenDto.getChartType();
        if (StringUtils.isNotBlank(chartType)) {
            goal += ",请使用" + chartType;
        }
        long modelId = 1708117271022800898L;
        StringBuilder message = new StringBuilder();
        message.append("分析需求:").append("\n");
        message.append(goal).append("\n");
        message.append("原始数据:").append("\n");
        String data = ExcelUtils.excelToCsv(chartGenDto.getFile());
        message.append(data);
        String result = ChatUtils.doChat(modelId, message.toString());
        String[] split = result.split("##########");
        if (split.length != 2) {
            throw new CustomizeReturnException(ReturnCode.FAIL, "AI 生成错误");
        }
        String genChart = split[0];
        String genResult = split[1];
        Chart chart = new Chart();
        chart.setName(name);
        chart.setGoal(goal);
        chart.setData(data);
        chart.setType(chartType);
        chart.setGenChart(genChart);
        chart.setGenResult(genResult);
        chart.setUserId(LoginUtils.getLoginUserId());
        boolean saveResult = chartService.save(chart);
        if (!saveResult) {
            throw new CustomizeReturnException(ReturnCode.ERRORS_OCCURRED_IN_THE_DATABASE_SERVICE);
        }
        ChartGenVo biResponse = new ChartGenVo();
        biResponse.setGenChart(genChart);
        biResponse.setGenResult(genResult);
        return R.ok(biResponse);
    }

    /**
     * 获取查询包装类
     *
     * @param chartPageDto
     * @return
     */
    private LambdaQueryWrapper<Chart> getQueryWrapper(ChartPageDto chartPageDto) {
        LambdaQueryWrapper<Chart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (chartPageDto == null) {
            return lambdaQueryWrapper;
        }
        Long id = chartPageDto.getId();
        String name = chartPageDto.getName();
        String goal = chartPageDto.getGoal();
        String chartType = chartPageDto.getChartType();
        Long userId = chartPageDto.getUserId();
        // 拼接查询条件
        lambdaQueryWrapper.like(StringUtils.isNotBlank(goal), Chart::getGoal, goal);
        lambdaQueryWrapper.like(StringUtils.isNotBlank(name), Chart::getName, name);
        lambdaQueryWrapper.like(StringUtils.isNotBlank(chartType), Chart::getType, chartType);
        lambdaQueryWrapper.eq(ObjectUtils.isNotEmpty(id) && id > 0, Chart::getId, id);
        lambdaQueryWrapper.eq(ObjectUtils.isNotEmpty(userId), Chart::getUserId, userId);
        return lambdaQueryWrapper;
    }
}
