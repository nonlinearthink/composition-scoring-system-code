package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.dao.PushArticleEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.PushArticleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService
{
    private final PushArticleEntityMapper pushArticleEntityMapper;

    @Autowired(required = false)
    public ArticleService(PushArticleEntityMapper pushArticleEntityMapper)
    {
        this.pushArticleEntityMapper = pushArticleEntityMapper;
    }
    public int addArticle(PushArticleEntity pushArticleEntity)
    {
        pushArticleEntityMapper.insert(pushArticleEntity);
        return pushArticleEntity.getArticleId();
    }
    public List<PushArticleEntity> selectAllArticles()
    {
        return pushArticleEntityMapper.selectAll();
    }
    public void deleteArticle(Integer articleId)
    {
        pushArticleEntityMapper.deleteByPrimaryKey(articleId);
    }
    public void updateArticle(PushArticleEntity pushArticleEntity, Integer articleId)
    {
        pushArticleEntity.setArticleId(articleId);
        pushArticleEntityMapper.updateByPrimaryKeySelective(pushArticleEntity);
    }
}
