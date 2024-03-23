package top.sharehome.springbootinittemplate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.sharehome.springbootinittemplate.model.entity.User;

/**
 * 用户Mapper类
 *
 * @author AntonyCheng
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select user_account from t_user where user_id = #{userId}")
    String selectAccountById(Long userId);

}