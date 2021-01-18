package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.dao.FeedbackEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.FeedbackEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Tuenity
 */
@Service
public class FeedbackService
{
    private final FeedbackEntityMapper feedbackEntityMapper;

    @Autowired(required = false)
    public FeedbackService(FeedbackEntityMapper feedbackEntityMapper)
    {
        this.feedbackEntityMapper = feedbackEntityMapper;
    }
    public int addFeedback(FeedbackEntity feedbackEntity)
    {
        feedbackEntity.setStatus(0);
        feedbackEntityMapper.insert(feedbackEntity);
        return feedbackEntity.getFeedbackId();
    }
    public List<FeedbackEntity> selectAllFeedbacks()
    {
        return feedbackEntityMapper.selectAll();
    }
    public int updateFeedback(FeedbackEntity feedbackEntity, Integer feedbackId)
    {
        feedbackEntity.setFeedbackId(feedbackId);
        return feedbackEntityMapper.updateByPrimaryKeySelective(feedbackEntity);
    }
}
