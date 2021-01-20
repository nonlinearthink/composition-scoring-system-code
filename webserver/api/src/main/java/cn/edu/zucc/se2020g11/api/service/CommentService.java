package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.dao.CommentEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.CommentEntity;
import cn.edu.zucc.se2020g11.api.model.CommentViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论服务层
 *
 * @author Tuenity
 */
@Service
public class CommentService
{
    private final CommentEntityMapper commentEntityMapper;

    @Autowired(required = false)
    public CommentService(CommentEntityMapper commentEntityMapper)
    {
        this.commentEntityMapper = commentEntityMapper;
    }

    /**
     * 获取所有评论
     *
     * @return 评论列表
     */
    public List<CommentEntity> selectAllComments()
    {
        return commentEntityMapper.selectAll();
    }

    /**
     * 获取指定文章评论
     *
     * @param compositionId 文章ID
     * @return 评论列表
     */
    public List<CommentEntity> selectComment(Integer compositionId)
    {
        return commentEntityMapper.selectAllSelective(compositionId);
    }

    /**
     * 获取指定评论
     *
     * @param commentId 评论ID
     * @return 评论实体
     */
    public CommentEntity selectCommentById(Integer commentId)
    {
        return commentEntityMapper.selectByPrimaryKey(commentId);
    }

    /**
     * 获取评论视图
     *
     * @param targetUsername 目标用户名
     * @return 评论视图列表
     */
    public List<CommentViewModel> selectCommentView(String targetUsername)
    {
        return commentEntityMapper.selectCommentView(targetUsername);
    }

    /**
     * 添加评论
     *
     * @param commentEntity 评论实体
     * @return 评论ID
     */
    public int addComment(CommentEntity commentEntity)
    {
        commentEntity.setStatus(0);
        commentEntityMapper.insert(commentEntity);
        return commentEntity.getCommentId();
    }

    /**
     * 删除评论
     *
     * @param commentId 评论ID
     * @return 是否删除成功
     */
    public boolean deleteComment(Integer commentId)
    {
        commentEntityMapper.deleteByPrimaryKey(commentId);
        return true;
    }

    /**
     * 删除指定文章评论
     *
     * @param compositionId 文章ID
     * @return 是否删除成功
     */
    public boolean deleteCommentByCompositionId(Integer compositionId)
    {
        commentEntityMapper.deleteByCompositionId(compositionId);
        return true;
    }

    /**
     * 更新评论状态
     *
     * @param commentEntity 评论实体
     * @param commentId 评论ID
     * @return 是否更新成功
     */
    public int updateCommentByStatus(CommentEntity commentEntity, Integer commentId)
    {
        commentEntity.setCommentId(commentId);
        return commentEntityMapper.updateByStatus(commentEntity);
    }
}
