package top.sharehome.springbootinittemplate.model.vo.chart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Ai 生成的返回结果
 *
 * @author AntonyCheng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ChartGenVo implements Serializable {

    private static final long serialVersionUID = -539879739760406303L;

    private String genChart;

    private String genResult;

}
