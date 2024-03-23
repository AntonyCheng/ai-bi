package top.sharehome.springbootinittemplate.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;
import top.sharehome.springbootinittemplate.common.validate.PutGroup;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 单文件Dto类
 *
 * @author AntonyCheng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SingleFileDto implements Serializable {

    private static final long serialVersionUID = 9188629538630154406L;

    /**
     * 文件
     */
    @NotNull(message = "文件不能为空1", groups = PutGroup.class)
    private MultipartFile file;

}
