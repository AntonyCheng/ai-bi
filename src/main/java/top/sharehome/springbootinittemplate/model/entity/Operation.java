package top.sharehome.springbootinittemplate.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作类
 *
 * @author AntonyCheng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_operation")
@Accessors(chain = true)
public class Operation implements Serializable {

    /**
     * id
     */
    @TableId(value = "operation_id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 操作接口
     */
    @TableField(value = "operation_api")
    private String api;

    /**
     * 操作人ID
     */
    @TableField(value = "operation_user_id")
    private Long userId;

    /**
     * 操作人账号
     */
    @TableField(value = "operation_user_account")
    private String userAccount;

    /**
     * 操作描述
     */
    @TableField(value = "operation_description")
    private String description;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableField(value = "is_delete")
    @TableLogic
    private Integer isDeleted;

    private static final long serialVersionUID = -4019252873830784857L;
}