package com.wf.aibi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wf.aibi.model.entity.Chart;
import org.apache.ibatis.annotations.Mapper;

/**
* @author admin
* @description 针对表【chart(图标信息表)】的数据库操作Mapper
* @createDate 2023-09-29 22:13:57
* @Entity generator.domain.Chart
*/
@Mapper
public interface ChartMapper extends BaseMapper<Chart> {

}




