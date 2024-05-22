package top.sharehome.springbootinittemplate.service.impl;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.sharehome.springbootinittemplate.common.base.ReturnCode;
import top.sharehome.springbootinittemplate.exception.customize.CustomizeReturnException;
import top.sharehome.springbootinittemplate.exception.customize.CustomizeTransactionException;
import top.sharehome.springbootinittemplate.mapper.ChartMapper;
import top.sharehome.springbootinittemplate.mapper.FileMapper;
import top.sharehome.springbootinittemplate.mapper.UserMapper;
import top.sharehome.springbootinittemplate.model.dto.chart.ChartGenDto;
import top.sharehome.springbootinittemplate.model.dto.chart.ChartPageDto;
import top.sharehome.springbootinittemplate.model.entity.Chart;
import top.sharehome.springbootinittemplate.model.entity.File;
import top.sharehome.springbootinittemplate.model.entity.User;
import top.sharehome.springbootinittemplate.model.vo.chart.ChartAdminPageVo;
import top.sharehome.springbootinittemplate.model.vo.chart.ChartGenVo;
import top.sharehome.springbootinittemplate.model.vo.chart.ChartUserPageVo;
import top.sharehome.springbootinittemplate.service.ChartService;
import top.sharehome.springbootinittemplate.utils.chat.ChatUtils;
import top.sharehome.springbootinittemplate.utils.excel.ExcelUtils;
import top.sharehome.springbootinittemplate.utils.oss.minio.MinioUtils;
import top.sharehome.springbootinittemplate.utils.satoken.LoginUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 图表服务接口实现类
 *
 * @author AntonyCheng
 */
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart> implements ChartService {

    private static final List<String> SUFFIX_LIST = new ArrayList<String>() {
        {
            add("xlsx");
            add("xls");
        }
    };

    @Resource
    private ChartMapper chartMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private FileMapper fileMapper;

    @Override
    @Transactional(readOnly = true, rollbackFor = CustomizeTransactionException.class)
    public Page<ChartAdminPageVo> pageAdminChart(ChartPageDto chartPageDto) {
        Page<Chart> page = new Page<>(chartPageDto.getPage(), chartPageDto.getSize());
        Page<ChartAdminPageVo> res = new Page<>(chartPageDto.getPage(), chartPageDto.getSize());
        LambdaQueryWrapper<Chart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Chart::getCreateTime);
        lambdaQueryWrapper.like(StringUtils.isNotBlank(chartPageDto.getGoal()), Chart::getGoal, chartPageDto.getGoal());
        lambdaQueryWrapper.like(StringUtils.isNotBlank(chartPageDto.getName()), Chart::getName, chartPageDto.getName());
        lambdaQueryWrapper.like(StringUtils.isNotBlank(chartPageDto.getChartType()), Chart::getType, chartPageDto.getChartType());
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.like(User::getAccount, chartPageDto.getUserAccount());
        List<Long> userIdList = userMapper.selectList(userLambdaQueryWrapper).stream().map(User::getId).collect(Collectors.toList());
        if (StringUtils.isNotEmpty(chartPageDto.getUserAccount()) && userIdList.isEmpty()) {
            return new Page<>(chartPageDto.getPage(), chartPageDto.getSize());
        }
        lambdaQueryWrapper.in(!userIdList.isEmpty(), Chart::getUserId, userIdList);
        chartMapper.selectPage(page, lambdaQueryWrapper);
        List<ChartAdminPageVo> chartAdminPageVoList = page.getRecords().stream().map(chart -> {
            ChartAdminPageVo chartAdminPageVo = new ChartAdminPageVo();
            String userAccount = userMapper.selectAccountById(chart.getUserId());
            chartAdminPageVo.setUserId(chart.getUserId());
            chartAdminPageVo.setUserAccount(userAccount);
            BeanUtils.copyProperties(chart, chartAdminPageVo);
            return chartAdminPageVo;
        }).collect(Collectors.toList());
        BeanUtils.copyProperties(page, res, "records");
        res.setRecords(chartAdminPageVoList);
        return res;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = CustomizeTransactionException.class)
    public Page<ChartUserPageVo> pageUserChart(ChartPageDto chartPageDto) {
        Page<Chart> page = new Page<>(chartPageDto.getPage(), chartPageDto.getSize());
        Page<ChartUserPageVo> res = new Page<>(chartPageDto.getPage(), chartPageDto.getSize());
        LambdaQueryWrapper<Chart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Chart::getCreateTime);
        lambdaQueryWrapper.like(StringUtils.isNotBlank(chartPageDto.getGoal()), Chart::getGoal, chartPageDto.getGoal());
        lambdaQueryWrapper.like(StringUtils.isNotBlank(chartPageDto.getName()), Chart::getName, chartPageDto.getName());
        lambdaQueryWrapper.like(StringUtils.isNotBlank(chartPageDto.getChartType()), Chart::getType, chartPageDto.getChartType());
        lambdaQueryWrapper.eq(Chart::getUserId, LoginUtils.getLoginUserId());
        chartMapper.selectPage(page, lambdaQueryWrapper);
        List<ChartUserPageVo> chartUserPageVoList = page.getRecords().stream().map(chart -> {
            ChartUserPageVo chartUserPageVo = new ChartUserPageVo();
            BeanUtils.copyProperties(chart, chartUserPageVo);
            return chartUserPageVo;
        }).collect(Collectors.toList());
        BeanUtils.copyProperties(page, res, "records");
        res.setRecords(chartUserPageVoList);
        return res;
    }

    @Override
    @Transactional(rollbackFor = CustomizeTransactionException.class)
    public ChartGenVo genChartByAi(ChartGenDto chartGenDto) {
        long size = chartGenDto.getFile().getSize();
        if (size > 2 * 1024 * 1024) {
            throw new CustomizeReturnException(ReturnCode.USER_UPLOADED_FILE_IS_TOO_LARGE, "文件大于2MB");
        }
        String originalFilename = chartGenDto.getFile().getOriginalFilename();
        String suffix = FileUtil.getSuffix(originalFilename);
        if (!SUFFIX_LIST.contains(suffix)) {
            throw new CustomizeReturnException(ReturnCode.USER_UPLOADED_FILE_TYPE_MISMATCH, "仅支持xlsx和xls文件");
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
            throw new CustomizeReturnException(ReturnCode.FAIL, "AI 生成错误，请重试！");
        }
        String genChart = split[0];
        String genResult = split[1];
        CompletableFuture.supplyAsync(() -> {
            return 200;
        });
        Chart chart = new Chart();
        chart.setName(name);
        chart.setGoal(goal);
        chart.setData(data);
        chart.setType(chartType);
        chart.setGenChart(genChart);
        chart.setGenResult(genResult);
        chart.setUserId(LoginUtils.getLoginUserId());
        int insertResult = chartMapper.insert(chart);
        if (insertResult == 0) {
            throw new CustomizeReturnException(ReturnCode.ERRORS_OCCURRED_IN_THE_DATABASE_SERVICE);
        }
        ChartGenVo biResponse = new ChartGenVo();
        biResponse.setGenChart(genChart);
        biResponse.setGenResult(genResult);
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String filePath = "file/" + date;
        String url = MinioUtils.upload(chartGenDto.getFile(), filePath);
        File file = new File()
                .setChartId(chart.getId())
                .setUserId(LoginUtils.getLoginUserId())
                .setName(originalFilename)
                .setSuffix(suffix)
                .setUrl(url);
        int insertFileResult = fileMapper.insert(file);
        if (insertFileResult == 0) {
            throw new CustomizeReturnException(ReturnCode.ERRORS_OCCURRED_IN_THE_DATABASE_SERVICE);
        }
        return biResponse;
    }

    @Override
    @Transactional(rollbackFor = CustomizeTransactionException.class)
    public void deleteChart(Long id) {
        Chart chartInDatabase = chartMapper.selectById(id);
        if (Objects.isNull(chartInDatabase)) {
            return;
        }
        int deleteResult = chartMapper.deleteById(id);
        if (deleteResult == 0) {
            throw new CustomizeReturnException(ReturnCode.ERRORS_OCCURRED_IN_THE_DATABASE_SERVICE);
        }
        LambdaQueryWrapper<File> fileLambdaQueryWrapper = new LambdaQueryWrapper<>();
        fileLambdaQueryWrapper.eq(File::getChartId, id);
        fileMapper.delete(fileLambdaQueryWrapper);
    }

    @Override
    @Transactional(rollbackFor = CustomizeTransactionException.class)
    public void deleteUserChart(Long id) {
        Chart chartInDatabase = chartMapper.selectById(id);
        if (Objects.isNull(chartInDatabase)) {
            return;
        }
        if (!Objects.equals(chartInDatabase.getUserId(), LoginUtils.getLoginUserId())) {
            return;
        }
        int deleteResult = chartMapper.deleteById(id);
        if (deleteResult == 0) {
            throw new CustomizeReturnException(ReturnCode.ERRORS_OCCURRED_IN_THE_DATABASE_SERVICE);
        }
        LambdaQueryWrapper<File> fileLambdaQueryWrapper = new LambdaQueryWrapper<>();
        fileLambdaQueryWrapper.eq(File::getChartId, id);
        fileMapper.delete(fileLambdaQueryWrapper);
    }

}




