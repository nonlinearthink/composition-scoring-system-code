package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.CompositionReportEntity;
import cn.edu.zucc.se2020g11.api.model.ReportModel;
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
class CompositionReportEntityMapperTest
{
    @Autowired
    private CompositionReportEntityMapper compositionReportEntityMapper;

    @Test
    void selectAll()
    {
        assertThat(compositionReportEntityMapper.selectAll()).isNotEmpty()
                .hasOnlyElementsOfType(CompositionReportEntity.class);
    }

    @Test
    void selectAllReports()
    {
        assertThat(compositionReportEntityMapper.selectAllReports()).isNotEmpty()
                .hasOnlyElementsOfType(ReportModel.class);
    }
}