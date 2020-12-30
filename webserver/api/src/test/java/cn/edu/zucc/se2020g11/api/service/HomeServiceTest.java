package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.model.ArticleModel;
import cn.edu.zucc.se2020g11.api.model.FollowCardModel;
import cn.edu.zucc.se2020g11.api.model.HotCardModel;
import cn.edu.zucc.se2020g11.api.model.NewCardModel;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class HomeServiceTest
{

    @Autowired
    private HomeService homeService;

    @Test
    void selectAllArticles()
    {
        List<ArticleModel> articleModelList = homeService.selectAllArticles();

        assertThat(articleModelList).isNotEmpty()
                .hasOnlyElementsOfType(ArticleModel.class);
    }

    @Test
    void selectFollowCompositions()
    {
        List<FollowCardModel> followCardModelList = homeService.selectFollowCompositions("test");

        assertThat(followCardModelList).isNotEmpty()
                .hasOnlyElementsOfType(FollowCardModel.class);
    }

    @Test
    void selectNewCompositions()
    {
        List<NewCardModel> newCardModelList = homeService.selectNewCompositions();

        assertThat(newCardModelList).isNotEmpty()
                .hasOnlyElementsOfType(NewCardModel.class);
    }

    @Test
    void selectHotCompositions()
    {
        List<HotCardModel> hotCardModelList = homeService.selectHotCompositions();

        assertThat(hotCardModelList).isNotEmpty()
                .hasOnlyElementsOfType(HotCardModel.class);
    }

}