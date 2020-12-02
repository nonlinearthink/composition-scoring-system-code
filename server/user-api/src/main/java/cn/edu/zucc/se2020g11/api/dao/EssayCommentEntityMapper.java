package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.EssayCommentEntity;

public interface EssayCommentEntityMapper {
    int deleteByPrimaryKey(Integer eCommentId);

    int insert(EssayCommentEntity record);

    int insertSelective(EssayCommentEntity record);

    EssayCommentEntity selectByPrimaryKey(Integer eCommentId);

    int updateByPrimaryKeySelective(EssayCommentEntity record);

    int updateByPrimaryKey(EssayCommentEntity record);
}