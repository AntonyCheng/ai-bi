package top.sharehome.springbootinittemplate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.sharehome.springbootinittemplate.exception.customize.CustomizeTransactionException;
import top.sharehome.springbootinittemplate.mapper.ChartMapper;
import top.sharehome.springbootinittemplate.mapper.FileMapper;
import top.sharehome.springbootinittemplate.mapper.UserMapper;
import top.sharehome.springbootinittemplate.model.dto.file.FilePageDto;
import top.sharehome.springbootinittemplate.model.entity.Chart;
import top.sharehome.springbootinittemplate.model.entity.File;
import top.sharehome.springbootinittemplate.model.entity.User;
import top.sharehome.springbootinittemplate.model.page.PageModel;
import top.sharehome.springbootinittemplate.model.vo.file.FileExportVo;
import top.sharehome.springbootinittemplate.model.vo.file.FilePageVo;
import top.sharehome.springbootinittemplate.service.FileService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件服务实现类
 *
 * @author AntonyCheng
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

    @Resource
    private FileMapper fileMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ChartMapper chartMapper;

    @Override
    @Transactional(readOnly = true, rollbackFor = CustomizeTransactionException.class)
    public Page<FilePageVo> pageFile(FilePageDto filePageDto, PageModel pageModel) {
        Page<File> page = pageModel.build(File.class);
        Page<FilePageVo> res = pageModel.build(FilePageVo.class);

        LambdaQueryWrapper<File> fileLambdaQueryWrapper = new LambdaQueryWrapper<>();
        fileLambdaQueryWrapper.like(StringUtils.isNotBlank(filePageDto.getName()), File::getName, filePageDto.getName())
                .eq(StringUtils.isNotBlank(filePageDto.getSuffix()), File::getSuffix, filePageDto.getSuffix());

        if (StringUtils.isNotBlank(filePageDto.getUserAccount())) {
            LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userLambdaQueryWrapper.like(User::getAccount, filePageDto.getUserAccount());
            List<Long> userIdList = userMapper.selectList(userLambdaQueryWrapper).stream().map(User::getId).collect(Collectors.toList());
            if (userIdList.isEmpty()) {
                return res;
            } else {
                fileLambdaQueryWrapper.in(File::getUserId, userIdList);
            }
        }

        if (StringUtils.isNotBlank(filePageDto.getChartName())) {
            LambdaQueryWrapper<Chart> chartLambdaQueryWrapper = new LambdaQueryWrapper<>();
            chartLambdaQueryWrapper.like(Chart::getName, filePageDto.getChartName());
            List<Long> chartIdList = chartMapper.selectList(chartLambdaQueryWrapper).stream().map(Chart::getId).collect(Collectors.toList());
            if (chartIdList.isEmpty()) {
                return res;
            } else {
                fileLambdaQueryWrapper.in(File::getChartId, chartIdList);
            }
        }

        fileLambdaQueryWrapper.orderByDesc(File::getCreateTime);
        fileMapper.selectPage(page, fileLambdaQueryWrapper);
        List<FilePageVo> filePageVoList = page.getRecords().stream().map(file -> {
            FilePageVo filePageVo = new FilePageVo();
            filePageVo.setId(file.getId());
            filePageVo.setName(file.getName());
            filePageVo.setUrl(file.getUrl());
            filePageVo.setSuffix(file.getSuffix());
            filePageVo.setUserAccount(userMapper.selectById(file.getUserId()).getAccount());
            filePageVo.setChartName(chartMapper.selectById(file.getChartId()).getName());
            filePageVo.setCreateTime(file.getCreateTime());
            return filePageVo;
        }).collect(Collectors.toList());
        BeanUtils.copyProperties(page, res, "records");
        res.setRecords(filePageVoList);
        return res;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = CustomizeTransactionException.class)
    public List<FileExportVo> exportExcelList() {
        List<File> filesInDatabase = fileMapper.selectList(null);
        return filesInDatabase.stream().map(file -> {
            FileExportVo fileExportVo = new FileExportVo();
            fileExportVo.setId(file.getId());
            fileExportVo.setName(file.getName());
            fileExportVo.setUrl(file.getUrl());
            fileExportVo.setSuffix(file.getSuffix());
            fileExportVo.setUserAccount(userMapper.selectById(file.getUserId()).getAccount());
            fileExportVo.setChartName(chartMapper.selectById(file.getChartId()).getName());
            fileExportVo.setCreateTime(file.getCreateTime());
            fileExportVo.setUpdateTime(file.getUpdateTime());
            return fileExportVo;
        }).collect(Collectors.toList());
    }

}




