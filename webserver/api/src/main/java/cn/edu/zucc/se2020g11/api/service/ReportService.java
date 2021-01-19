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
 * 举报服务层
 *
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

    /**
     * 添加文章举报
     *
     * @param compositionReportEntity 文章举报实体
     * @return 文章举报ID
     */
    public int addCompositionReport(CompositionReportEntity compositionReportEntity)
    {
        compositionReportEntity.setStatus(0);
        compositionReportEntityMapper.insert(compositionReportEntity);
        return compositionReportEntity.getCompositionReportId();
    }

    /**
     * 添加评论举报
     *
     * @param commentReportEntity 评论举报实体
     * @return 评论举报ID
     */
    public int addCommentReport(CommentReportEntity commentReportEntity)
    {
        commentReportEntity.setStatus(0);
        commentReportEntityMapper.insert(commentReportEntity);
        return commentReportEntity.getCommentReportId();
    }

    /**
     * 获取所有举报
     *
     * @return 举报列表
     */
    public List<ReportModel> selectAllReports()
    {
        return compositionReportEntityMapper.selectAllReports();
    }

    /**
     * 获取文章举报
     *
     * @return 文章举报列表
     */
    public List<CompositionReportEntity> selectAllCompositionReports()
    {
        return compositionReportEntityMapper.selectAll();
    }

    /**
     * 获取评论举报
     *
     * @return 评论举报列表
     */
    public List<CommentReportEntity> selectAllCommentReports()
    {
        return commentReportEntityMapper.selectAll();
    }

    /**
     * 更新文章举报
     *
     * @param compositionReportEntity 文章举报实体
     * @param compositionReportId 文章举报ID
     * @return 是否更新成功
     */
    public int updateCompositionReport(CompositionReportEntity compositionReportEntity, Integer compositionReportId)
    {
        compositionReportEntity.setCompositionReportId(compositionReportId);
        return compositionReportEntityMapper.updateByPrimaryKeySelective(compositionReportEntity);
    }

    /**
     * 更新文章举报
     *
     * @param commentReportEntity 评论举报实体
     * @param commentReportId 评论举报ID
     * @return 是否更新成功
     */
    public int updateCommentReport(CommentReportEntity commentReportEntity, Integer commentReportId)
    {
        commentReportEntity.setCommentReportId(commentReportId);
        return commentReportEntityMapper.updateByPrimaryKeySelective(commentReportEntity);
    }
}
