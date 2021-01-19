package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.dao.FeedbackEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.FeedbackEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 反馈服务层
 *
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

    /**
     * 添加反馈
     *
     * @param feedbackEntity 反馈实体
     * @return 反馈ID
     */
    public int addFeedback(FeedbackEntity feedbackEntity)
    {
        feedbackEntity.setStatus(0);
        feedbackEntityMapper.insert(feedbackEntity);
        return feedbackEntity.getFeedbackId();
    }

    /**
     * 获取反馈
     *
     * @return 反馈列表
     */
    public List<FeedbackEntity> selectAllFeedbacks()
    {
        return feedbackEntityMapper.selectAll();
    }

    /**
     * 更新反馈
     *
     * @param feedbackEntity 错误实体
     * @param feedbackId 反馈ID
     * @return 是否更新成功
     */
    public int updateFeedback(FeedbackEntity feedbackEntity, Integer feedbackId)
    {
        feedbackEntity.setFeedbackId(feedbackId);
        return feedbackEntityMapper.updateByPrimaryKeySelective(feedbackEntity);
    }
}
