package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.dao.CommentReportEntityMapper;
import cn.edu.zucc.se2020g11.api.dao.CompositionReportEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.CommentReportEntity;
import cn.edu.zucc.se2020g11.api.entity.CompositionReportEntity;
import cn.edu.zucc.se2020g11.api.model.ReportModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Tuenity
 */
@Service
public class ReportService
{
    private final CompositionReportEntityMapper compositionReportEntityMapper;
    private final CommentReportEntityMapper commentReportEntityMapper;

    @Autowired(required = false)
    public ReportService(CompositionReportEntityMapper compositionReportEntityMapper, CommentReportEntityMapper commentReportEntityMapper)
    {
        this.compositionReportEntityMapper = compositionReportEntityMapper;
        this.commentReportEntityMapper = commentReportEntityMapper;
    }
    public int addCompositionReport(CompositionReportEntity compositionReportEntity)
    {
        compositionReportEntity.setStatus(0);
        compositionReportEntityMapper.insert(compositionReportEntity);
        return compositionReportEntity.getCompositionReportId();
    }
    public int addCommentReport(CommentReportEntity commentReportEntity)
    {
        commentReportEntity.setStatus(0);
        commentReportEntityMapper.insert(commentReportEntity);
        return commentReportEntity.getCommentReportId();
    }
    public List<ReportModel> selectAllReports()
    {
        return compositionReportEntityMapper.selectAllReports();
    }
    public List<CompositionReportEntity> selectAllCompositionReports()
    {
        return compositionReportEntityMapper.selectAll();
    }
    public List<CommentReportEntity> selectAllCommentReports()
    {
        return commentReportEntityMapper.selectAll();
    }
    public int updateCompositionReport(CompositionReportEntity compositionReportEntity, Integer compositionReportId)
    {
        compositionReportEntity.setCompositionReportId(compositionReportId);
        return compositionReportEntityMapper.updateByPrimaryKeySelective(compositionReportEntity);
    }
    public int updateCommentReport(CommentReportEntity commentReportEntity, Integer commentReportId)
    {
        commentReportEntity.setCommentReportId(commentReportId);
        return commentReportEntityMapper.updateByPrimaryKeySelective(commentReportEntity);
    }
}
