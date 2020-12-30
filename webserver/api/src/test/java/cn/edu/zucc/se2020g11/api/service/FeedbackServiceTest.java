package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.entity.FeedbackEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class FeedbackServiceTest
{

    @Autowired
    private FeedbackService feedbackService;

    @Test
    void addFeedback()
    {
        FeedbackEntity feedbackEntity = new FeedbackEntity();
        feedbackEntity.setUsername("test");
        feedbackEntity.setFeedbackBody("test");
        feedbackEntity.setFeedbackType("1");

        int num = feedbackService.addFeedback(feedbackEntity);
        assertThat(num).isGreaterThan(0);
    }

    @Test
    void selectAllFeedbacks()
    {
        List<FeedbackEntity> feedbackEntityList = feedbackService.selectAllFeedbacks();

        assertThat(feedbackEntityList).isNotEmpty()
                .hasOnlyElementsOfType(FeedbackEntity.class);
    }

    @Test
    void updateFeedback()
    {
        FeedbackEntity feedbackEntity = new FeedbackEntity();
        feedbackEntity.setStatus(0);

        int num = feedbackService.updateFeedback(feedbackEntity, 2);
        assertThat(num).isGreaterThan(0);
    }
}