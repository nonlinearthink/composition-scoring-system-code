package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.FollowEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class FollowEntityMapperTest
{
    @Autowired
    private FollowEntityMapper followEntityMapper;

    @Test
    void deleteByUsernameAndTargetUsername()
    {
        FollowEntity followEntity = new FollowEntity();
        followEntity.setUsername("test");
        followEntity.setTargetUsername("nonlinearthink");
        assertThat(followEntityMapper.deleteByUsernameAndTargetUsername(followEntity)).isGreaterThan(0);
    }

    @Test
    void selectAllSelective()
    {
        FollowEntity followEntity = new FollowEntity();
        followEntity.setUsername("test");
        assertThat(followEntityMapper.selectAllSelective(followEntity)).isNotEmpty()
                .hasOnlyElementsOfType(FollowEntity.class);
    }

    @Test
    void selectAllSelectiveByUser()
    {
        FollowEntity followEntity = new FollowEntity();
        followEntity.setTargetUsername("nonlinearthink");
        assertThat(followEntityMapper.selectAllSelectiveByUser(followEntity)).isNotEmpty()
                .hasOnlyElementsOfType(FollowEntity.class);
    }

    @Test
    void selectByUsernameAndTargetUsername()
    {
        FollowEntity followEntity = new FollowEntity();
        followEntity.setUsername("test");
        followEntity.setTargetUsername("nonlinearthink");
        assertThat(followEntityMapper.selectByUsernameAndTargetUsername(followEntity)).isNotEmpty()
                .hasOnlyElementsOfType(FollowEntity.class);
    }
}