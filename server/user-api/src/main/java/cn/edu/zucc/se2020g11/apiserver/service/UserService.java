package cn.edu.zucc.se2020g11.apiserver.service;

import cn.edu.zucc.se2020g11.apiserver.bean.User;
import cn.edu.zucc.se2020g11.apiserver.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService
{

    private UserMapper userMapper;

    @Autowired(required = false)
    public UserService(UserMapper userMapper)
    {
        this.userMapper = userMapper;
    }
    public void insert(User user)
    {
        userMapper.insert(user);
    }

}
