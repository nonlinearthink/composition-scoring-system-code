package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.PushArticle;

public interface PushArticleMapper {
    int deleteByPrimaryKey(Integer articleId);

    int insert(PushArticle record);

    int insertSelective(PushArticle record);

    PushArticle selectByPrimaryKey(Integer articleId);

    int updateByPrimaryKeySelective(PushArticle record);

    int updateByPrimaryKey(PushArticle record);
}