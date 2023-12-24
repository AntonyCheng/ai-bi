package top.sharehome.springbootinittemplate.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 分页实体类
 *
 * @author AntonyCheng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PageModel implements Serializable {

    @NotNull(message = "分页参数为空")
    private Long page;

    @NotNull(message = "分页参数为空")
    @Size(min = 1, message = "分页参数无效")
    private Long pageSize;

    private static final long serialVersionUID = -1649075566134281161L;
}
