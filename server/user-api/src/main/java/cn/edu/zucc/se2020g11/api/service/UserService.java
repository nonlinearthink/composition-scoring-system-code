package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import cn.edu.zucc.se2020g11.api.dao.UserMapper;
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
    public void insert(UserEntity userEntity)
    {
        userMapper.insert(userEntity);
    }

}
