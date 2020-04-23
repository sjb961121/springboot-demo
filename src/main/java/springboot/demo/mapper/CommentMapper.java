package springboot.demo.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import springboot.demo.model.Comment;

public interface CommentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    List<Comment> selectAllByParentIdAndType(@Param("parentId")Long parentId,@Param("type")Integer type);


}