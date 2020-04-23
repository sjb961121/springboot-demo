package springboot.demo.mapper;

import org.apache.ibatis.annotations.Param;import springboot.demo.model.User;import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectAllByToken(@Param("token") String token);

    User selectByToken(@Param("token") String token);

    User selectById(@Param("id") Long id);

    User selectByAccountId(@Param("accountId") String accountId);

    int updateById(@Param("updated") User updated, @Param("id") Long id);
}