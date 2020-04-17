package springboot.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import springboot.demo.model.User;

@Mapper
public interface UserMapper {
    @Insert("Insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtcreate},#{gmtmodified},#{avatarUrl})")
    public void insert(User user);

    @Select("select * from user where token=#{token}")
    User selectBytoken(@Param("token") String token);

    @Select("select * from user where id=#{id}")
    User selectById(@Param("id") Integer id);
}

