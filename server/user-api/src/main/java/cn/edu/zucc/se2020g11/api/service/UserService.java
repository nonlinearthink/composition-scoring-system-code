package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.dao.UserEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService
{

    private final UserEntityMapper userEntityMapper;

    @Autowired(required = false)
    public UserService(UserEntityMapper userEntityMapper)
    {
        this.userEntityMapper = userEntityMapper;
    }
    public void insert(UserEntity userEntity)
    {
        userEntityMapper.insert(userEntity);
    }

}
