package top.sharehome.springbootinittemplate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.sharehome.springbootinittemplate.common.base.ReturnCode;
import top.sharehome.springbootinittemplate.exception.customize.CustomizeReturnException;
import top.sharehome.springbootinittemplate.mapper.OperationMapper;
import top.sharehome.springbootinittemplate.model.dto.operation.OperationAddDto;
import top.sharehome.springbootinittemplate.model.dto.operation.OperationPageDto;
import top.sharehome.springbootinittemplate.model.entity.Operation;
import top.sharehome.springbootinittemplate.model.page.PageModel;
import top.sharehome.springbootinittemplate.model.vo.operation.OperationPageVo;
import top.sharehome.springbootinittemplate.service.OperationService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 操作服务实现类
 *
 * @author AntonyCheng
 */
@Service
public class OperationServiceImpl extends ServiceImpl<OperationMapper, Operation> implements OperationService {

    @Resource
    private OperationMapper operationMapper;

    @Override
    public Page<OperationPageVo> pageOperation(OperationPageDto operationPageDto, PageModel pageModel) {
        Page<Operation> page = pageModel.build();
        Page<OperationPageVo> res = pageModel.build();

        LambdaQueryWrapper<Operation> operationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        operationLambdaQueryWrapper
                .like(StringUtils.isNotBlank(operationPageDto.getApi()), Operation::getApi, operationPageDto.getApi())
                .like(StringUtils.isNotBlank(operationPageDto.getUserAccount()), Operation::getUserAccount, operationPageDto.getUserAccount())
                .like(StringUtils.isNotBlank(operationPageDto.getDescription()), Operation::getDescription, operationPageDto.getDescription());
        operationLambdaQueryWrapper.orderByAsc(Operation::getCreateTime);

        operationMapper.selectPage(page, operationLambdaQueryWrapper);

        List<OperationPageVo> newRecords = page.getRecords().stream().map(operation -> {
            OperationPageVo operationPageVo = new OperationPageVo();
            operationPageVo.setId(operation.getId());
            operationPageVo.setApi(operation.getApi());
            operationPageVo.setUserId(operation.getUserId());
            operationPageVo.setUserAccount(operation.getUserAccount());
            operationPageVo.setDescription(operation.getDescription());
            operationPageVo.setCreateTime(operation.getCreateTime());
            return operationPageVo;
        }).collect(Collectors.toList());
        BeanUtils.copyProperties(page, res, "records");
        res.setRecords(newRecords);

        return res;
    }

    @Override
    public void addOperation(OperationAddDto operationAddDto) {
        Operation operation = new Operation();
        BeanUtils.copyProperties(operationAddDto, operation);
        int insertResult = operationMapper.insert(operation);
        if (insertResult == 0) {
            throw new CustomizeReturnException(ReturnCode.ERRORS_OCCURRED_IN_THE_DATABASE_SERVICE);
        }
    }

    @Override
    public void clearOperation() {
        List<Long> ids = operationMapper.selectList(null).stream().map(Operation::getId).collect(Collectors.toList());
        int deleteResult = operationMapper.deleteBatchIds(ids);
        if (deleteResult == 0) {
            throw new CustomizeReturnException(ReturnCode.ERRORS_OCCURRED_IN_THE_DATABASE_SERVICE);
        }
    }

}




