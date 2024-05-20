package top.sharehome.springbootinittemplate.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 文件类
 *
 * @author AntonyCheng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_file")
@Accessors(chain = true)
public class File implements Serializable {

    /**
     * ID
     */
    @TableId(value = "file_id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 文件名称
     */
    @TableField(value = "file_name")
    private String name;

    /**
     * 文件地址
     */
    @TableField(value = "file_url")
    private String url;

    /**
     * 文件格式
     */
    @TableField(value = "file_suffix")
    private String suffix;

    /**
     * 用户id
     */
    @TableField(value = "file_user_id")
    private Long userId;

    /**
     * 图表id
     */
    @TableField(value = "file_chart_id")
    private Long chartId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

    private static final long serialVersionUID = -6822402837546511161L;
}