package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.CompositionCommentEntity;

public interface CompositionCommentEntityMapper {
    int deleteByPrimaryKey(Integer ccommentId3);

    int insert(CompositionCommentEntity record);

    int insertSelective(CompositionCommentEntity record);

    CompositionCommentEntity selectByPrimaryKey(Integer ccommentId3);

    int updateByPrimaryKeySelective(CompositionCommentEntity record);

    int updateByPrimaryKey(CompositionCommentEntity record);
}