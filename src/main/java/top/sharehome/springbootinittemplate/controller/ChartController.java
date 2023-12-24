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
import org.springframework.web.multipart.MultipartFile;
import top.sharehome.springbootinittemplate.common.base.R;
import top.sharehome.springbootinittemplate.common.base.ReturnCode;
import top.sharehome.springbootinittemplate.exception.customize.CustomizeReturnException;
import top.sharehome.springbootinittemplate.model.dto.chart.ChartGenDto;
import top.sharehome.springbootinittemplate.model.dto.chart.ChartQueryDto;
import top.sharehome.springbootinittemplate.model.entity.Chart;
import top.sharehome.springbootinittemplate.model.entity.PageModel;
import top.sharehome.springbootinittemplate.model.vo.chart.ChartGenVo;
import top.sharehome.springbootinittemplate.service.impl.ChartService;
import top.sharehome.springbootinittemplate.utils.chat.ChatUtils;
import top.sharehome.springbootinittemplate.utils.excel.ExcelUtils;
import top.sharehome.springbootinittemplate.utils.satoken.LoginUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
     * todo 修改返回参数
     *
     * @param chartQueryDto 分页查询Dto类
     * @param pageModel     分页实体类
     * @return
     */
    @PostMapping("/user/list/page")
    public R<Page<Chart>> listMyChartByPage(@Validated @RequestBody PageModel pageModel, @Validated @RequestBody ChartQueryDto chartQueryDto) {
        chartQueryDto.setUserId(LoginUtils.getLoginUserId());
        long page = pageModel.getPage();
        long size = pageModel.getPageSize();
        Page<Chart> chartPage = chartService.page(new Page<>(page, size),
                getQueryWrapper(chartQueryDto));
        return R.ok(chartPage);
    }

    /**
     * 智能分析
     *
     * @param multipartFile
     * @param chartGenDto
     * @param request
     * @return
     */
    @PostMapping("/gen")
    public R<ChartGenVo> genChartByAi(@RequestPart("file") MultipartFile multipartFile, ChartGenDto chartGenDto, HttpServletRequest request) {
        long size = multipartFile.getSize();
        if (size > 1024 * 1024) {
            throw new CustomizeReturnException(ReturnCode.USER_UPLOADED_FILE_IS_TOO_LARGE);
        }
        String originalFilename = multipartFile.getOriginalFilename();
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
        String data = ExcelUtils.excelToCsv(multipartFile);
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
        chart.setChartData(data);
        chart.setChartType(chartType);
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
     * @param chartQueryDto
     * @return
     */
    private LambdaQueryWrapper<Chart> getQueryWrapper(ChartQueryDto chartQueryDto) {
        LambdaQueryWrapper<Chart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (chartQueryDto == null) {
            return lambdaQueryWrapper;
        }
        Long id = chartQueryDto.getId();
        String name = chartQueryDto.getName();
        String goal = chartQueryDto.getGoal();
        String chartType = chartQueryDto.getChartType();
        Long userId = chartQueryDto.getUserId();
        // 拼接查询条件
        lambdaQueryWrapper.like(StringUtils.isNotBlank(goal), Chart::getGoal, goal);
        lambdaQueryWrapper.like(StringUtils.isNotBlank(name), Chart::getName, name);
        lambdaQueryWrapper.like(StringUtils.isNotBlank(chartType), Chart::getChartType, chartType);
        lambdaQueryWrapper.eq(ObjectUtils.isNotEmpty(id) && id > 0, Chart::getId, id);
        lambdaQueryWrapper.eq(ObjectUtils.isNotEmpty(userId), Chart::getUserId, userId);
        return lambdaQueryWrapper;
    }
}
