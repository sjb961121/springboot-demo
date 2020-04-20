package springboot.demo.mapper;

import org.apache.ibatis.annotations.Param;import springboot.demo.model.Question;import java.util.List;

public interface QuestionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Question record);

    int insertSelective(Question record);

    Question selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKey(Question record);

    Question selectById(@Param("id") Integer id);

    List<Question> selectAll();

    List<Question> selectAllByCreator(@Param("creator") Integer creator);

    int updateById(@Param("updated") Question updated, @Param("id") Integer id);
}