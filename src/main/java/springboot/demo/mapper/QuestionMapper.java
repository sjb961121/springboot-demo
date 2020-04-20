package springboot.demo.mapper;

import org.apache.ibatis.annotations.*;
import springboot.demo.model.Question;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Select("select * from question where id=#{id}")
    Question getQuestionByid(@Param("id") Integer id);

    @Insert("insert into question (title,description,tag,gmt_create,gmt_modified,creator) values (#{title},#{description},#{tag},#{gmtCreate},#{gmtModified},#{creator})")
    public void insert(Question question);

    @Select("select * from question")
    List<Question> list();

    @Select("select * from question where creator=#{id}")
    List<Question> listByUserId(@Param("id") Integer id);

    @Update("update question set title=#{title},description=#{description},gmt_modified=#{gmtModified},tag=#{tag} where id=#{id}")
    void update(Question question);
}
