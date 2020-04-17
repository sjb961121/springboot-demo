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

    public List<QuestionDTO> list(Integer page, Integer size,Model model){
        PageHelper.startPage(page,size);
        List<Question> questions=questionMapper.list();
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        PageInfo pageInfo = new PageInfo(questions);
        for (Question question:questions){
            User user=userMapper.selectById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        model.addAttribute("pageInfo",pageInfo);
//        System.out.println(model);
//        System.out.println(pageInfo);

        return questionDTOList;

    }

    public List<QuestionDTO> listByUserId(Integer page,Integer size,Integer id,Model model){
        PageHelper.startPage(page,size);
        List<Question> questions=questionMapper.listByUserId(id);
        PageInfo pageInfo = new PageInfo(questions);
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for (Question question:questions){
            User user=userMapper.selectById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        model.addAttribute("pageInfo",pageInfo);
        return questionDTOList;
    }
}
