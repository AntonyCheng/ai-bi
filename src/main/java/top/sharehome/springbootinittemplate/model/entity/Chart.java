package top.sharehome.springbootinittemplate.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 图标信息表
 *
 * @author AntonyCheng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_chart")
public class Chart implements Serializable {
    /**
     * id
     */
    @TableId(value = "chart_id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 图表名称
     */
    @TableField(value = "chart_name")
    private String name;

    /**
     * 分析目标
     */
    @TableField(value = "chart_goal")
    private String goal;

    /**
     * 图标数据
     */
    @TableField(value = "chart_data")
    private String data;

    /**
     * 图表类型
     */
    @TableField(value = "chart_type")
    private String type;

    /**
     * 生成的图表数据
     */
    @TableField(value = "chart_gen_chart")
    private String genChart;

    /**
     * 生成的分析结论
     */
    @TableField(value = "chart_gen_result")
    private String genResult;

    /**
     * 用户id
     */
    @TableField(value = "chart_user_id")
    private Long userId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 逻辑删除（0表示未删除，1表示已删除）
     */
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    @TableLogic
    private Integer isDeleted;

    private static final long serialVersionUID = 91865469458078099L;

    public static final String COL_CHART_ID = "chart_id";

    public static final String COL_CHART_NAME = "chart_name";

    public static final String COL_CHART_GOAL = "chart_goal";

    public static final String COL_CHART_DATA = "chart_data";

    public static final String COL_CHART_TYPE = "chart_type";

    public static final String COL_CHART_GEN_CHART = "chart_gen_chart";

    public static final String COL_CHART_GEN_RESULT = "chart_gen_result";

    public static final String COL_CHART_USER_ID = "chart_user_id";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";

    public static final String COL_IS_DELETED = "is_deleted";
}