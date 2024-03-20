package top.sharehome.springbootinittemplate.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.sharehome.springbootinittemplate.common.validate.GetGroup;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 分页实体类
 *
 * @author AntonyCheng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageModel implements Serializable {

    @NotNull(groups = {GetGroup.class}, message = "分页参数不能为空")
    @Min(value = 1, groups = {GetGroup.class}, message = "分页参数错误")
    private Long page;

    @NotNull(groups = {GetGroup.class}, message = "分页参数不能为空")
    @Min(value = 1, groups = {GetGroup.class}, message = "分页参数错误")
    @Max(value = 100, groups = {GetGroup.class}, message = "分页参数错误")
    private Long size;

}
