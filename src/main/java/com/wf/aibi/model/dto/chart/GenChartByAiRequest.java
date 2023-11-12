package com.wf.aibi.model.dto.chart;

import lombok.Data;

import java.io.Serializable;

/**
 * 通过Ai生成请求
 *
 * @author AntonyCheng
 * @since 2023/9/30 15:24:34
 */
@Data
public class GenChartByAiRequest  implements Serializable {
    /**
     * 图表名称
     */
    private String name;

    /**
     * 分析目标
     */
    private String goal;

    /**
     * 图表类型
     */
    private String chartType;

    private static final long serialVersionUID = 1L;
}
