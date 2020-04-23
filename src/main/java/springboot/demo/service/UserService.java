package springboot.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.demo.mapper.UserMapper;
import springboot.demo.model.User;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


    public void createorupdate(User u) {
        User user=userMapper.selectByAccountId(u.getAccountId());
        if (user==null){
            u.setGmtCreate(System.currentTimeMillis());
            u.setGmtModified(u.getGmtCreate());
            userMapper.insert(u);
        }
        else{
            user.setAvatarUrl(u.getAvatarUrl());
            user.setName(u.getName());
            user.setToken(u.getToken());
            user.setGmtModified(System.currentTimeMillis());
            userMapper.updateById(user,user.getId());
        }
    }

}
