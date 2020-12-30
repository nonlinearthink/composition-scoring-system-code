package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserEntityMapperTest
{
    @Autowired
    private UserEntityMapper userEntityMapper;

    @Test
    void selectByEmail()
    {
        assertThat(userEntityMapper.selectByEmail("1577239190@qq.com")).isInstanceOf(UserEntity.class);
    }

    @Test
    void selectByUsernameAndEmail()
    {
        assertThat(userEntityMapper.selectByUsernameAndEmail("test", "123")).isNull();
        assertThat(userEntityMapper.selectByUsernameAndEmail("test", "1577239190@qq.com")).isInstanceOf(UserEntity.class);
    }

    @Test
    void updateUser()
    {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("test");
        userEntity.setFrozen(false);
        assertThat(userEntityMapper.updateUser(userEntity)).isGreaterThan(0);
    }

    @Test
    void selectSum()
    {
        assertThat(userEntityMapper.selectSum()).isGreaterThan(0);
    }
}