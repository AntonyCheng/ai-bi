package com.wf.aibi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wf.aibi.model.dto.chart.ChartQueryRequest;
import com.wf.aibi.model.dto.post.PostQueryRequest;
import com.wf.aibi.model.entity.Chart;
import com.wf.aibi.model.entity.Post;

/**
* @author admin
* @description 针对表【chart(图标信息表)】的数据库操作Service
* @createDate 2023-09-29 22:13:57
*/
public interface ChartService extends IService<Chart> {
}
