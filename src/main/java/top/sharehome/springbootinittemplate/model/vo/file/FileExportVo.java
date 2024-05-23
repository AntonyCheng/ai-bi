package top.sharehome.springbootinittemplate.model.vo.file;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 分页查询文件信息Vo类
 *
 * @author AntonyCheng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileExportVo implements Serializable {

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 文件名称
     */
    @ExcelProperty(value = "文件名称")
    private String name;

    /**
     * 文件地址
     */
    @ExcelProperty(value = "文件地址")
    private String url;

    /**
     * 文件格式
     */
    @ExcelProperty(value = "文件格式")
    private String suffix;

    /**
     * 用户账号
     */
    @ExcelProperty(value = "用户账号")
    private String userAccount;

    /**
     * 图表名称
     */
    @ExcelProperty(value = "图表名称")
    private String chartName;

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

    private static final long serialVersionUID = -374282262791773450L;

}
