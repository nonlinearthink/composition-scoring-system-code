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
import static org.junit.jupiter.api.Assertions.*;

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

        assertThat(articleEntityList.size()).isGreaterThan(0);
    }

    @Test
    void deleteArticle()
    {
    }

    @Test
    void updateArticle()
    {
    }
}