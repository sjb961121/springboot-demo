package springboot.demo.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.demo.dto.CommentDTO;
import springboot.demo.enums.CommentEnum;
import springboot.demo.enums.NotificationEnum;
import springboot.demo.exception.CommentException;
import springboot.demo.exception.QuestionException;
import springboot.demo.mapper.CommentMapper;
import springboot.demo.mapper.NotificationMapper;
import springboot.demo.mapper.QuestionMapper;
import springboot.demo.mapper.UserMapper;
import springboot.demo.model.Comment;
import springboot.demo.model.Notification;
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
    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment,User user){
            if (comment.getParentId()==null||comment.getParentId()==0){
                throw new CommentException("未选中任何问题或评论进行回复",2003);
            }
            if (comment.getType()==null|| !CommentEnum.isExist(comment.getType())){
                throw new CommentException("评论类型参数错误",2004);
            }
            if (comment.getType()==CommentEnum.QUESTION.getType()){
                //回复问题
                Question question = questionMapper.selectById(comment.getParentId());
                if (question==null){
                    throw new QuestionException("你找到问题不在了，要不要换个试试？",2002);
                }
                commentMapper.insert(comment);
                questionMapper.updateIncCommentCountById(question.getId());
                //创建通知
                createNotification(comment, question.getCreator(),NotificationEnum.REPLY_QUESTION,user.getName(),question.getTitle(),question.getId());
            }
            else{
                //回复评论
                Comment com = commentMapper.selectByPrimaryKey(comment.getParentId());
                if (com==null){
                    throw new CommentException("回复的评论不存在了，要不要换个试试？",2005);
                }
                commentMapper.insert(comment);
                commentMapper.updateIncCommentCountById(comment.getParentId());
                //创建通知，command+option+m 创建方法
                createNotification(comment, com.getCommentator(),NotificationEnum.REPLY_COMMENT,user.getName(),com.getContent(),com.getParentId());
            }

    }

    private void createNotification(Comment comment, Long receiver,NotificationEnum notificationEnum,String notifierName,String outerTitle,Long outerId) {
        if (comment.getCommentator()==receiver){
            return;
        }
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationEnum.getType());
        notification.setStatus(0);
        notification.setOuterId(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
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
