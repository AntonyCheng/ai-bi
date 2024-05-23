package top.sharehome.springbootinittemplate.model.dto.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 分页查询文件信息Dto类
 *
 * @author AntonyCheng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class FilePageDto implements Serializable {

    /**
     * 文件名称
     */
    private String name;

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

    private static final long serialVersionUID = -3273112691821446980L;

}
