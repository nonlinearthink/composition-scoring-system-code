package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.constant.ErrorDictionary;
import cn.edu.zucc.se2020g11.api.constant.LogCategory;
import cn.edu.zucc.se2020g11.api.dao.CommentEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.CommentEntity;
import cn.edu.zucc.se2020g11.api.model.CommentViewModel;
import cn.edu.zucc.se2020g11.api.model.SupportViewModel;
import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService
{
    private final CommentEntityMapper commentEntityMapper;

    @Autowired(required = false)
    public CommentService(CommentEntityMapper commentEntityMapper)
    {
        this.commentEntityMapper = commentEntityMapper;
    }
    public List<CommentEntity> selectAllComments()
    {
        return commentEntityMapper.selectAll();
    }
    public List<CommentEntity> selectComment(Integer compositionId)
    {
        return commentEntityMapper.selectAllSelective(compositionId);
    }
    public CommentEntity selectCommentById(Integer commentId)
    {
        return commentEntityMapper.selectByPrimaryKey(commentId);
    }
    public List<CommentViewModel> selectCommentView(String targetUsername)
    {
        return commentEntityMapper.selectCommentView(targetUsername);
    }
    public int addComment(CommentEntity commentEntity)
    {
        commentEntity.setStatus(0);
        commentEntityMapper.insert(commentEntity);
        return commentEntity.getCommentId();
    }
    public int deleteComment(Integer commentId)
    {
        int num = commentEntityMapper.deleteByPrimaryKey(commentId);
        if(num == 0){
            throw new BaseException(ErrorDictionary.NO_SUPPORT, LogCategory.BUSINESS);
        }
        return num;
    }
    public int deleteCommentByCompositionId(Integer compositionId)
    {
        return commentEntityMapper.deleteByCompositionId(compositionId);
    }
    public int updateCommentByStatus(CommentEntity commentEntity, Integer commentId)
    {
        commentEntity.setCommentId(commentId);
        return commentEntityMapper.updateByStatus(commentEntity);
    }
}
