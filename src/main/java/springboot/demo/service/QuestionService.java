package springboot.demo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import springboot.demo.dto.QuestionDTO;
import springboot.demo.exception.QuestionException;
import springboot.demo.mapper.QuestionMapper;
import springboot.demo.mapper.UserMapper;
import springboot.demo.model.Question;
import springboot.demo.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PageInfo list(Integer page, Integer size){
        PageHelper.startPage(page,size);
        PageHelper.orderBy("gmt_create desc");
        List<Question> questions=questionMapper.selectAll();
        PageInfo pageInfo = new PageInfo(questions);
//        model.addAttribute("pageInfo",pageInfo);
//        System.out.println(model);
//        System.out.println(pageInfo);
        return pageInfo;

    }

    public PageInfo listByUserId(Integer page,Integer size,Long id){
        PageHelper.startPage(page,size);
        PageHelper.orderBy("gmt_create desc");
        List<Question> questions=questionMapper.selectAllByCreator(id);
        PageInfo pageInfo = new PageInfo(questions);
        return pageInfo;
    }

    public List<QuestionDTO> toQuestionDTO(List<Question> questions){
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for (Question question:questions){
            User user=userMapper.selectById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }

    public QuestionDTO getQuestionById(Long id) {
        Question question=questionMapper.selectById(id);
        if (question==null){
            throw new QuestionException("你找到问题不在了，要不要换个试试？",2002);
        }
        User user=userMapper.selectById(question.getCreator());
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createorupdate(Question question) {
        if (question.getId()==null){
            questionMapper.insert(question);
        }
        else {
            question.setGmtModified(question.getGmtCreate());
            questionMapper.updateById(question,question.getId());
        }
    }

    public void incView(Long id) {
        questionMapper.updateIncViewCountById(id);
    }

    public List<QuestionDTO> relatedQuestions(Long id) {
        Question question = questionMapper.selectById(id);
        String tag = question.getTag();
        if (StringUtils.isBlank(tag)){
            return new ArrayList<>();
        }
        String newtag = StringUtils.replace(tag, ",", "|");
        List<Question> relatedquestions = questionMapper.selectByTagRegexp(newtag, id);
        List<QuestionDTO> questionDTOList = relatedquestions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOList;
    }
}
