package springboot.demo.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.demo.dto.CommentDTO;
import springboot.demo.enums.CommentEnum;
import springboot.demo.exception.CommentException;
import springboot.demo.exception.QuestionException;
import springboot.demo.mapper.CommentMapper;
import springboot.demo.mapper.QuestionMapper;
import springboot.demo.mapper.UserMapper;
import springboot.demo.model.Comment;
import springboot.demo.model.Question;
import springboot.demo.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void insert(Comment comment){
            if (comment.getParentId()==null||comment.getParentId()==0){
                throw new CommentException("问题不存在",2003);
            }
            if (comment.getType()==null|| !CommentEnum.isExist(comment.getType())){
                throw new CommentException("评论类型参数错误",2004);
            }
            if (comment.getType()==CommentEnum.QUESTION.getType()){
                //回复问题
                Question question = questionMapper.selectById(comment.getParentId());
                if (question==null){
                    throw new QuestionException("问题不存在",2002);
                }
                commentMapper.insert(comment);
                questionMapper.updateIncCommentCountById(question.getId());
            }
            else{
                //回复评论
                Comment com = commentMapper.selectByPrimaryKey(comment.getParentId());
                if (com==null){
                    throw new CommentException("评论不存在",2005);
                }
                commentMapper.insert(comment);
            }

    }

    public List<CommentDTO> getCommentByQuestionId(Long id) {
        List<Comment> comments = commentMapper.selectAllByParentIdAndType(id, CommentEnum.QUESTION.getType());
        if (comments.size()==0){
            return new ArrayList<>();
        }
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long>userIds=new ArrayList<>();
        List<User>users=new ArrayList<>();
        userIds.addAll(commentators);
        for (Long userId : userIds) {
            users.add(userMapper.selectById(userId));
        }
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }

    public List<CommentDTO> getCommentByCommentId(Long id) {
        List<Comment> comments = commentMapper.selectAllByParentIdAndType(id, CommentEnum.COMMENT.getType());
        if (comments.size()==0){
            return new ArrayList<>();
        }
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long>userIds=new ArrayList<>();
        List<User>users=new ArrayList<>();
        userIds.addAll(commentators);
        for (Long userId : userIds) {
            users.add(userMapper.selectById(userId));
        }
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
}
