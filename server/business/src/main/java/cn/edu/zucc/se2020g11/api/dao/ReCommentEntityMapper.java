package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.ReCommentEntity;

public interface ReCommentEntityMapper {
    int deleteByPrimaryKey(Integer reCommentId);

    int insert(ReCommentEntity record);

    int insertSelective(ReCommentEntity record);

    ReCommentEntity selectByPrimaryKey(Integer reCommentId);

    int updateByPrimaryKeySelective(ReCommentEntity record);

    int updateByPrimaryKey(ReCommentEntity record);
}