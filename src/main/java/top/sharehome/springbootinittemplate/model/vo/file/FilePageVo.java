package top.sharehome.springbootinittemplate.model.vo.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 分页查询操作日志Vo类
 *
 * @author AntonyCheng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class FilePageVo implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件地址
     */
    private String url;

    /**
     * 文件格式
     */
    private String suffix;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 图表名称
     */
    private String chartName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    private static final long serialVersionUID = -374282262791773450L;

}
