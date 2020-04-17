package springboot.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import springboot.demo.model.Question;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,tag,gmt_create,gmt_modified,creator) values (#{title},#{description},#{tag},#{gmtCreate},#{gmtModified},#{creator})")
    public void insert(Question question);

    @Select("select * from question")
    List<Question> list();

    @Select("select * from question where creator=#{id}")
    List<Question> listByUserId(@Param("id") Integer id);
}
