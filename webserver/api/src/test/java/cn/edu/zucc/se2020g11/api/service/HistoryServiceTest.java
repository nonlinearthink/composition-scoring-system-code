package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.entity.HistoryEntity;
import cn.edu.zucc.se2020g11.api.model.HistoryModel;
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
class HistoryServiceTest
{

    @Autowired
    private HistoryService historyService;

    @Test
    void addHistory()
    {
        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setUsername("test");
        historyEntity.setCompositionId(1);

        int num = historyService.addHistory(historyEntity);
        assertThat(num).isGreaterThan(0);
    }

    @Test
    void selectHistoryByUser()
    {
        List<HistoryModel> historyModelList = historyService.selectHistoryByUser("test");

        assertThat(historyModelList).isNotEmpty()
                .hasOnlyElementsOfType(HistoryModel.class);
    }

    @Test
    void deleteHistoryByCompositionId()
    {
        int num = historyService.deleteHistoryByCompositionId(1);

        assertThat(num).isGreaterThan(0);
    }
}