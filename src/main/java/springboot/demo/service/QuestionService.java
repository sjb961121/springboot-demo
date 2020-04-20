package springboot.demo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import springboot.demo.dto.QuestionDTO;
import springboot.demo.mapper.QuestionMapper;
import springboot.demo.mapper.UserMapper;
import springboot.demo.model.Question;
import springboot.demo.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PageInfo list(Integer page, Integer size){
        PageHelper.startPage(page,size);
        List<Question> questions=questionMapper.list();
        PageInfo pageInfo = new PageInfo(questions);
//        model.addAttribute("pageInfo",pageInfo);
//        System.out.println(model);
//        System.out.println(pageInfo);
        return pageInfo;

    }

    public PageInfo listByUserId(Integer page,Integer size,Integer id){
        PageHelper.startPage(page,size);
        List<Question> questions=questionMapper.listByUserId(id);
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

    public QuestionDTO getQuestionById(Integer id) {
        Question question=questionMapper.getQuestionByid(id);
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
            questionMapper.update(question);
        }
    }
}
