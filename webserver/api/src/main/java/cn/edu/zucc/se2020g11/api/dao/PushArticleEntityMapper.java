package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.PushArticleEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PushArticleEntityMapper
{
    int deleteByPrimaryKey(Integer articleId);

    int insert(PushArticleEntity record);

    int insertSelective(PushArticleEntity record);

    PushArticleEntity selectByPrimaryKey(Integer articleId);

    List<PushArticleEntity> selectAll();

    int updateByPrimaryKeySelective(PushArticleEntity record);

    int updateByPrimaryKey(PushArticleEntity record);
}