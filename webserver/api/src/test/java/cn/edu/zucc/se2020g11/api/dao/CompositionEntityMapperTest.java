package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.CompositionEntity;
import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import cn.edu.zucc.se2020g11.api.model.CompositionCountModel;
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
class CompositionEntityMapperTest
{
    @Autowired
    private CompositionEntityMapper compositionEntityMapper;

    @Test
    void selectByUsername()
    {
        assertThat(compositionEntityMapper.selectByUsername("test")).isNotEmpty()
                .hasOnlyElementsOfType(CompositionEntity.class);
    }

    @Test
    void updateByValid()
    {
        CompositionEntity compositionEntity = new CompositionEntity();
        compositionEntity.setCompositionId(1);
        compositionEntity.setValid(0);
        assertThat(compositionEntityMapper.updateByValid(compositionEntity)).isGreaterThan(0);
    }

    @Test
    void selectAllSelective()
    {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("test");
        assertThat(compositionEntityMapper.selectAllSelective(userEntity)).isNotEmpty()
                .hasOnlyElementsOfType(CompositionEntity.class);
    }

    @Test
    void selectFollow()
    {
        assertThat(compositionEntityMapper.selectFollow("test")).isNotEmpty()
                .hasOnlyElementsOfType(CompositionEntity.class);
    }

    @Test
    void selectNew()
    {
        assertThat(compositionEntityMapper.selectNew()).isNotEmpty()
                .hasOnlyElementsOfType(CompositionEntity.class);
    }

    @Test
    void selectHot()
    {
        assertThat(compositionEntityMapper.selectHot()).isNotEmpty()
                .hasOnlyElementsOfType(CompositionCountModel.class);
    }

    @Test
    void selectCountByCompositionId()
    {
        assertThat(compositionEntityMapper.selectCountByCompositionId(1)).isInstanceOf(CompositionCountModel.class);
    }

    @Test
    void selectSum()
    {
        assertThat(compositionEntityMapper.selectSum()).isGreaterThan(0);
    }
}