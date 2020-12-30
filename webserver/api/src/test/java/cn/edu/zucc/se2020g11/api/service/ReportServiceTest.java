package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.entity.CommentReportEntity;
import cn.edu.zucc.se2020g11.api.entity.CompositionReportEntity;
import cn.edu.zucc.se2020g11.api.model.ReportModel;
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
class ReportServiceTest
{

    @Autowired
    private ReportService reportService;

    @Test
    void addCompositionReport()
    {
        CompositionReportEntity compositionReportEntity = new CompositionReportEntity();
        compositionReportEntity.setCompositionId(1);
        compositionReportEntity.setUsername("test");

        int num = reportService.addCompositionReport(compositionReportEntity);
        assertThat(num).isGreaterThan(0);
    }

    @Test
    void addCommentReport()
    {
        CommentReportEntity commentReportEntity = new CommentReportEntity();
        commentReportEntity.setCommentId(1);
        commentReportEntity.setUsername("test");

        int num = reportService.addCommentReport(commentReportEntity);
        assertThat(num).isGreaterThan(0);
    }

    @Test
    void selectAllReports()
    {
        List<ReportModel> reportModelList = reportService.selectAllReports();

        assertThat(reportModelList).isNotEmpty()
                .hasOnlyElementsOfType(ReportModel.class);
    }

    @Test
    void selectAllCompositionReports()
    {
        List<CompositionReportEntity> compositionReportEntityList = reportService.selectAllCompositionReports();

        assertThat(compositionReportEntityList).isNotEmpty()
                .hasOnlyElementsOfType(CompositionReportEntity.class);
    }

    @Test
    void selectAllCommentReports()
    {
        List<CommentReportEntity> compositionReportEntityList = reportService.selectAllCommentReports();

        assertThat(compositionReportEntityList).isNotEmpty()
                .hasOnlyElementsOfType(CommentReportEntity.class);
    }

    @Test
    void updateCompositionReport()
    {
        CompositionReportEntity compositionReportEntity = new CompositionReportEntity();
        compositionReportEntity.setStatus(1);

        int num = reportService.updateCompositionReport(compositionReportEntity, 1);
        assertThat(num).isGreaterThan(0);
    }

    @Test
    void updateCommentReport()
    {
        CommentReportEntity commentReportEntity = new CommentReportEntity();
        commentReportEntity.setStatus(1);

        int num = reportService.updateCommentReport(commentReportEntity, 1);
        assertThat(num).isGreaterThan(0);
    }
}