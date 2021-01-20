package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.entity.CompositionEntity;
import cn.edu.zucc.se2020g11.api.entity.FavoriteEntity;
import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class FavoriteServiceTest
{

    @Autowired
    private FavoriteService favoriteService;

    @Test
    void addFavorite()
    {
        // 正常收藏
        FavoriteEntity favoriteEntity = new FavoriteEntity();
        favoriteEntity.setUsername("unit");
        favoriteEntity.setCompositionId(338);

        int num = favoriteService.addFavorite(favoriteEntity);
        assertThat(num).isGreaterThan(0);

        // 重复收藏
        favoriteEntity = new FavoriteEntity();
        favoriteEntity.setUsername("unit");
        favoriteEntity.setCompositionId(336);

        FavoriteEntity finalFavoriteEntity = favoriteEntity;
        assertThatExceptionOfType(BaseException.class).isThrownBy(() -> favoriteService.addFavorite(finalFavoriteEntity));
    }

    @Test
    void selectAllFavorites()
    {
        List<FavoriteEntity> favoriteEntityList = favoriteService.selectAllFavorites("unit");

        assertThat(favoriteEntityList).isNotEmpty()
                .hasOnlyElementsOfType(FavoriteEntity.class);
    }

    @Test
    void findFavoriteComposition()
    {
        List<FavoriteEntity> favoriteEntityList = new ArrayList<>();
        FavoriteEntity favoriteEntity = new FavoriteEntity();
        favoriteEntity.setUsername("unit");
        favoriteEntity.setCompositionId(336);
        favoriteEntityList.add(favoriteEntity);

        List<CompositionEntity> compositionEntityList = favoriteService.findFavoriteComposition(favoriteEntityList);
        assertThat(compositionEntityList).isNotEmpty()
                .hasOnlyElementsOfType(CompositionEntity.class);
    }

    @Test
    void findFavorite()
    {
        Boolean isFavorite = favoriteService.findFavorite("unit", 336);

        assertThat(isFavorite).isTrue();
    }

    @Test
    void deleteFavorite()
    {
        // 正常取消收藏
        FavoriteEntity favoriteEntity = new FavoriteEntity();
        favoriteEntity.setUsername("unit");
        favoriteEntity.setCompositionId(336);

        int num = favoriteService.deleteFavorite(favoriteEntity);
        assertThat(num).isGreaterThan(0);

        // 未收藏取消收藏
        favoriteEntity = new FavoriteEntity();
        favoriteEntity.setUsername("unit");
        favoriteEntity.setCompositionId(338);

        FavoriteEntity finalFavoriteEntity = favoriteEntity;
        assertThatExceptionOfType(BaseException.class).isThrownBy(() -> favoriteService.deleteFavorite(finalFavoriteEntity));
    }

    @Test
    void deleteFavoriteByCompositionId()
    {
        int num = favoriteService.deleteFavoriteByCompositionId(336);

        assertThat(num).isGreaterThan(0);
    }
}