package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserEntityMapperTest
{

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Test
    void selectByEmail()
    {
    }

    @Test
    void selectByUsernameAndEmail()
    {
    }

    @Test
    void updateUser()
    {
    }

    @Test
    void selectSum()
    {
    }
}