package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.FeedbackEntity;

import java.util.List;

public interface FeedbackEntityMapper {
    int deleteByPrimaryKey(Integer feedbackId);

    int insert(FeedbackEntity record);

    int insertSelective(FeedbackEntity record);

    FeedbackEntity selectByPrimaryKey(Integer feedbackId);

    List<FeedbackEntity> selectAll();

    int updateByPrimaryKeySelective(FeedbackEntity record);

    int updateByPrimaryKey(FeedbackEntity record);
}