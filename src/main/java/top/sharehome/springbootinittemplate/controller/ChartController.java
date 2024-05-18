package top.sharehome.springbootinittemplate.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.sharehome.springbootinittemplate.common.base.R;
import top.sharehome.springbootinittemplate.common.validate.GetGroup;
import top.sharehome.springbootinittemplate.common.validate.PostGroup;
import top.sharehome.springbootinittemplate.model.dto.chart.ChartGenDto;
import top.sharehome.springbootinittemplate.model.dto.chart.ChartPageDto;
import top.sharehome.springbootinittemplate.model.dto.operation.OperationAddDto;
import top.sharehome.springbootinittemplate.model.vo.auth.AuthLoginVo;
import top.sharehome.springbootinittemplate.model.vo.chart.ChartGenVo;
import top.sharehome.springbootinittemplate.model.vo.chart.ChartUserPageVo;
import top.sharehome.springbootinittemplate.service.ChartService;
import top.sharehome.springbootinittemplate.service.OperationService;
import top.sharehome.springbootinittemplate.utils.satoken.LoginUtils;

import javax.annotation.Resource;

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

    @Resource
    private OperationService operationService;

    /**
     * 分页获取当前用户创建的资源列表
     *
     * @param chartPageDto 分页查询Dto类
     * @return 分页结果
     */
    @PostMapping("/page")
    public R<Page<ChartUserPageVo>> pageChart(@Validated(GetGroup.class) @RequestBody ChartPageDto chartPageDto) {
        Page<ChartUserPageVo> chartPage = chartService.pageUserChart(chartPageDto);
        AuthLoginVo loginUser = LoginUtils.getLoginUser();
        operationService.addOperation(new OperationAddDto("/chart/page", loginUser.getId(), loginUser.getAccount(), "分页获取当前用户" + "[id=" + loginUser.getId() + "]" + "创建的资源列表"));
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
        ChartGenVo biResponse = chartService.genChartByAi(chartGenDto);
        AuthLoginVo loginUser = LoginUtils.getLoginUser();
        operationService.addOperation(new OperationAddDto("/chart/gen", loginUser.getId(), loginUser.getAccount(), "用户" + "[id=" + loginUser.getId() + "]执行智能分析操作"));
        return R.ok(biResponse);
    }

}
