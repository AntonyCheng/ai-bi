package top.sharehome.springbootinittemplate.model.dto.chart;

import lombok.Data;
import top.sharehome.springbootinittemplate.common.validate.PostGroup;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 通过Ai生成请求
 *
 * @author AntonyCheng
 */
@Data
public class ChartGenDto implements Serializable {
    /**
     * 图表名称
     */
    @NotEmpty(message = "图表名称不能为空", groups = {PostGroup.class})
    private String name;

    /**
     * 分析目标
     */
    @NotEmpty(message = "分析目标不能为空", groups = {PostGroup.class})
    private String goal;

    /**
     * 图表类型
     */
    @NotEmpty(message = "图表类型不能为空", groups = {PostGroup.class})
    private String chartType;

    private static final long serialVersionUID = -8308107490110660374L;
}
