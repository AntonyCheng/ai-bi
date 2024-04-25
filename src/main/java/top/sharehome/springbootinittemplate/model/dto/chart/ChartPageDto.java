package top.sharehome.springbootinittemplate.model.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.sharehome.springbootinittemplate.model.page.PageModel;

import java.io.Serializable;

/**
 * 查询请求
 *
 * @author AntonyCHeng
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ChartPageDto extends PageModel implements Serializable {

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
     * 用户账号
     */
    private String userAccount;

    private static final long serialVersionUID = -8211825557632072540L;
}