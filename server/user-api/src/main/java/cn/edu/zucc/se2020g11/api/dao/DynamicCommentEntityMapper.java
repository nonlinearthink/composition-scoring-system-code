package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.DynamicCommentEntity;

public interface DynamicCommentEntityMapper {
    int deleteByPrimaryKey(Integer dcommentId2);

    int insert(DynamicCommentEntity record);

    int insertSelective(DynamicCommentEntity record);

    DynamicCommentEntity selectByPrimaryKey(Integer dcommentId2);

    int updateByPrimaryKeySelective(DynamicCommentEntity record);

    int updateByPrimaryKey(DynamicCommentEntity record);
}