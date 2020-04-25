package springboot.demo.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springboot.demo.dto.CommentCreateDTO;
import springboot.demo.dto.CommentDTO;
import springboot.demo.dto.ResultDTO;
import springboot.demo.mapper.CommentMapper;
import springboot.demo.model.Comment;
import springboot.demo.model.User;
import springboot.demo.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
//           throw  new CommentException("未登录，不能进行评论，请先登录",2001);
            return ResultDTO.errorOf("未登录，不能进行评论，请先登录",2001);
        }
        Comment comment = new Comment();
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0);
        comment.setCommentCount(0);

        commentService.insert(comment,user);
//        commentMapper.insert(comment);
//        HashMap<Object,Object> map=new HashMap<>();
//        map.put("message","成功");
//        map.put("code",200);
//        return map;
        return ResultDTO.okof();
    }

//    @ResponseBody
//    @RequestMapping(value = "/comment/{id}",method = RequestMethod.POST)
//    public Object subcomment(@RequestBody CommentDTO commentDTO, @PathVariable("id") Long id, Model model){
//        List<CommentDTO> commentDTOList = commentService.getCommentByCommentId(id);
////        model.addAttribute("subcomment",commentDTOList);
//        return ResultDTO.okof(commentDTOList);
//    }

    @ResponseBody
    @GetMapping(value = "/comment/{id}")
    public Object show(@PathVariable("id") Long id, Model model){
        List<CommentDTO> commentDTOList = commentService.getCommentByCommentId(id);
//        model.addAttribute("subcomment",commentDTOList);
        return ResultDTO.okof(commentDTOList);
    }

}
