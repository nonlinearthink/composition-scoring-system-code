package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.entity.PushArticleEntity;
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
class ArticleServiceTest
{

    @Autowired
    private ArticleService articleService;

    @Test
    void addArticle()
    {
        PushArticleEntity pushArticleEntity = new PushArticleEntity();
        pushArticleEntity.setAdminName("dl");
        pushArticleEntity.setArticleTitle("test-title");
        pushArticleEntity.setArticleBody("test-body");

        int articleId = articleService.addArticle(pushArticleEntity);
        assertThat(articleId).isGreaterThan(0);
    }

    @Test
    void selectAllArticles()
    {
        List<PushArticleEntity> articleEntityList =  articleService.selectAllArticles();

        assertThat(articleEntityList).isNotEmpty()
            .hasOnlyElementsOfType(PushArticleEntity.class);
    }

    @Test
    void deleteArticle()
    {
        int num = articleService.deleteArticle(1);

        assertThat(num).isGreaterThan(0);
    }

    @Test
    void updateArticle()
    {
        PushArticleEntity pushArticleEntity = new PushArticleEntity();
        pushArticleEntity.setArticleTitle("test-title");
        pushArticleEntity.setArticleBody("test-body");

        int num = articleService.updateArticle(pushArticleEntity, 1);
        assertThat(num).isGreaterThan(0);
    }
}