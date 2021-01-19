package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.dao.PushArticleEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.PushArticleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 推送文章服务层
 *
 * @author Tuenity
 */
@Service
public class ArticleService
{
    private final PushArticleEntityMapper pushArticleEntityMapper;

    @Autowired(required = false)
    public ArticleService(PushArticleEntityMapper pushArticleEntityMapper)
    {
        this.pushArticleEntityMapper = pushArticleEntityMapper;
    }

    /**
     * 添加推送文章
     *
     * @param pushArticleEntity 推送文章实体
     * @return 新增文章ID
     */
    public int addArticle(PushArticleEntity pushArticleEntity)
    {
        pushArticleEntityMapper.insert(pushArticleEntity);
        return pushArticleEntity.getArticleId();
    }

    /**
     * 查看推送文章
     *
     * @return 文章列表
     */
    public List<PushArticleEntity> selectAllArticles()
    {
        return pushArticleEntityMapper.selectAll();
    }

    /**
     * 删除推送文章
     *
     * @param articleId 推送文章ID
     * @return 是否删除成功
     */
    public int deleteArticle(Integer articleId)
    {
        return pushArticleEntityMapper.deleteByPrimaryKey(articleId);
    }

    /**
     * 更新推送文章
     *
     * @param pushArticleEntity 推送文章实体
     * @param articleId 推送文章ID
     * @return 是否更新成功
     */
    public int updateArticle(PushArticleEntity pushArticleEntity, Integer articleId)
    {
        pushArticleEntity.setArticleId(articleId);
        return pushArticleEntityMapper.updateByPrimaryKeySelective(pushArticleEntity);
    }
}