package springboot.demo.mapper;

import org.apache.ibatis.annotations.Param;import springboot.demo.model.Question;import java.util.List;

public interface QuestionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Question record);

    int insertSelective(Question record);

    Question selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKey(Question record);

    Question selectById(@Param("id") Long id);

    List<Question> selectAll();

    List<Question> selectAllByCreator(@Param("creator") Long creator);

    int updateById(@Param("updated") Question updated, @Param("id") Long id);

    int updateIncViewCountById(@Param("id") Long id);

    int updateIncCommentCountById(@Param("id")Long id);

    List<Question> selectByTagRegexp(@Param("tag")String tag,@Param("id")Long id);

    List<Question> selectAllByTitleRegexp(@Param("title")String title);







}