package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.FeedbackEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FeedbackEntityMapper {
    int deleteByPrimaryKey(Integer feedbackId);

    int insert(FeedbackEntity record);

    int insertSelective(FeedbackEntity record);

    FeedbackEntity selectByPrimaryKey(Integer feedbackId);

    int updateByPrimaryKeySelective(FeedbackEntity record);

    int updateByPrimaryKey(FeedbackEntity record);
}