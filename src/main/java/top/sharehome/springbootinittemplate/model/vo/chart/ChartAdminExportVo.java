package top.sharehome.springbootinittemplate.model.vo.chart;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 管理员导出图表Vo类
 *
 * @author AntonyCheng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartAdminExportVo implements Serializable {

    /**
     * 图表ID
     */
    @ExcelProperty(value = "图表ID")
    private Long id;

    /**
     * 图表名称
     */
    @ExcelProperty(value = "图表名称")
    private String name;

    /**
     * 分析目标
     */
    @ExcelProperty(value = "分析目标")
    private String goal;

    /**
     * 图表类型
     */
    @ExcelProperty(value = "图表类型")
    private String type;

    /**
     * 生成的图表数据
     */
    @ExcelProperty(value = "生成的图表数据")
    private String genChart;

    /**
     * 生成的分析结论
     */
    @ExcelProperty(value = "生成的分析结论")
    private String genResult;

    /**
     * 用户账号
     */
    @ExcelProperty(value = "用户账号")
    private String userAccount;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    private static final long serialVersionUID = -7317650666114594035L;

}
