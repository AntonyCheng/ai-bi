package top.sharehome.springbootinittemplate.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.sharehome.springbootinittemplate.model.entity.Operation;
import top.sharehome.springbootinittemplate.service.OperationService;
import top.sharehome.springbootinittemplate.mapper.OperationMapper;
import org.springframework.stereotype.Service;

/**
 * 操作服务实现类
 *
 * @author AntonyCheng
 */
@Service
public class OperationServiceImpl extends ServiceImpl<OperationMapper, Operation>
    implements OperationService{

}




