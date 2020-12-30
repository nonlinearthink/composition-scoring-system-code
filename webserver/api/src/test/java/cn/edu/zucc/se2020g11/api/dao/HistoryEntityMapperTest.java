package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.HistoryEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class HistoryEntityMapperTest
{
    @Autowired
    private HistoryEntityMapper historyEntityMapper;

    @Test
    void selectHistoryByUser()
    {
        assertThat(historyEntityMapper.selectHistoryByUser("test")).isNotEmpty()
                .hasOnlyElementsOfType(HistoryEntity.class);
    }

    @Test
    void countHistory()
    {
        assertThat(historyEntityMapper.countHistory(1)).isGreaterThan(0);
    }
}