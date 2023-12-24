package top.sharehome.springbootinittemplate.model.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.sharehome.springbootinittemplate.common.validate.GetGroup;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 查询请求
 *
 * @author AntonyCHeng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ChartQueryDto implements Serializable {

    /**
     * id
     */
    @NotNull(message = "查询目标ID为空", groups = {GetGroup.class})
    private Long id;

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

    /**
     * 用户id
     */
    private Long userId;

    private static final long serialVersionUID = -8211825557632072540L;
}