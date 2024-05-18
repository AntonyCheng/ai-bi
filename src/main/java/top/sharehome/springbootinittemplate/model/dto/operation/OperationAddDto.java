package top.sharehome.springbootinittemplate.model.dto.operation;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 增加操作日志Dto类
 *
 * @author AntonyCheng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OperationAddDto implements Serializable {

    /**
     * 操作接口
     */
    private String api;

    /**
     * 操作人ID
     */
    private Long userId;

    /**
     * 操作人账号
     */
    private String userAccount;

    /**
     * 操作描述
     */
    private String description;

    private static final long serialVersionUID = 5333318827794245064L;

}
