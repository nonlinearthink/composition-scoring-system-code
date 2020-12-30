package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.SupportEntity;
import cn.edu.zucc.se2020g11.api.model.SupportViewModel;
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
class SupportEntityMapperTest
{
    @Autowired
    private SupportEntityMapper supportEntityMapper;

    @Test
    void deleteByUsernameAndCompositionId()
    {
        SupportEntity supportEntity = new SupportEntity();
        supportEntity.setUsername("test");
        supportEntity.setCompositionId(1);
        assertThat(supportEntityMapper.deleteByUsernameAndCompositionId(supportEntity)).isGreaterThan(0);
    }

    @Test
    void deleteByCompositionId()
    {
        assertThat(supportEntityMapper.deleteByCompositionId(1)).isGreaterThan(0);
    }

    @Test
    void selectAllSelective()
    {
        assertThat(supportEntityMapper.selectAllSelective("test")).isNotEmpty()
                .hasOnlyElementsOfType(SupportEntity.class);
    }

    @Test
    void selectByUsernameAndCompositionId()
    {
        SupportEntity supportEntity = new SupportEntity();
        supportEntity.setUsername("test");
        supportEntity.setCompositionId(1);
        assertThat(supportEntityMapper.selectByUsernameAndCompositionId(supportEntity)).isNotEmpty()
                .hasOnlyElementsOfType(SupportEntity.class);
    }

    @Test
    void countSupport()
    {
        assertThat(supportEntityMapper.countSupport(1)).isGreaterThan(0);
    }

    @Test
    void selectSupportView()
    {
        assertThat(supportEntityMapper.selectSupportView("test")).isNotEmpty()
                .hasOnlyElementsOfType(SupportViewModel.class);
    }
}