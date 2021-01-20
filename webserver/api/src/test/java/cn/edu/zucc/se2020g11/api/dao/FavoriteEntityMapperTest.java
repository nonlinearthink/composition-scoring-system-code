package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.FavoriteEntity;
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
class FavoriteEntityMapperTest
{
    @Autowired
    private FavoriteEntityMapper favoriteEntityMapper;

    @Test
    void deleteByUsernameAndCompositionId()
    {
        FavoriteEntity favoriteEntity = new FavoriteEntity();
        favoriteEntity.setUsername("unit");
        favoriteEntity.setCompositionId(336);
        assertThat(favoriteEntityMapper.deleteByUsernameAndCompositionId(favoriteEntity)).isGreaterThan(0);
    }

    @Test
    void deleteByCompositionId()
    {
        assertThat(favoriteEntityMapper.deleteByCompositionId(336)).isGreaterThan(0);
    }

    @Test
    void selectAllSelective()
    {
        assertThat(favoriteEntityMapper.selectAllSelective("unit")).isNotEmpty()
                .hasOnlyElementsOfType(FavoriteEntity.class);
    }

    @Test
    void selectByUsernameAndCompositionId()
    {
        FavoriteEntity favoriteEntity = new FavoriteEntity();
        favoriteEntity.setUsername("unit");
        favoriteEntity.setCompositionId(336);
        assertThat(favoriteEntityMapper.selectByUsernameAndCompositionId(favoriteEntity)).isNotEmpty()
                .hasOnlyElementsOfType(FavoriteEntity.class);
    }
}