package top.sharehome.springbootinittemplate.model.vo.operation;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 管理员导出操作信息表格Vo类
 *
 * @author AntonyCheng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationExportVo implements Serializable {

    /**
     * 操作ID
     */
    @ExcelProperty(value = "操作ID")
    private Long id;

    /**
     * 操作接口
     */
    @ExcelProperty(value = "操作接口")
    private String api;

    /**
     * 操作人ID
     */
    @ExcelProperty(value = "操作人ID")
    private Long userId;

    /**
     * 操作人账号
     */
    @ExcelProperty(value = "操作人账号")
    private String userAccount;

    /**
     * 操作描述
     */
    @ExcelProperty(value = "操作描述")
    private String description;

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

    private static final long serialVersionUID = 7106304080691772434L;

}
