package springboot.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springboot.demo.dto.AccessTokenDTO;
import springboot.demo.dto.GithubUser;
import springboot.demo.mapper.UserMapper;
import springboot.demo.model.User;
import springboot.demo.provider.GithubProvider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String client_id;
    @Value("${github.client.secret}")
    private String client_secret;
    @Value("${github.redirect.uri}")
    private String redirecturi;

    @Autowired
    private UserMapper userMapper;



    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code, @RequestParam(name = "state") String state,
                           HttpServletRequest request, HttpServletResponse response){
//        System.out.println("1");
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirecturi);
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
//        System.out.println(user.getName());
        if (user!=null){
            //登录成功
            User u = new User();
            String token = UUID.randomUUID().toString();
            u.setToken(token);
            u.setName(user.getName());
            u.setAccountId(String.valueOf(user.getId()));
            u.setGmtcreate(System.currentTimeMillis());
            u.setGmtmodified(u.getGmtcreate());
            userMapper.insert(u);
            response.addCookie(new Cookie("token",token));
//            request.getSession().setAttribute("user",user);
            return "redirect:/";
        }
        else{
            //登录失败
            return "redirect:/";
        }
//        return "index";
    }
}
