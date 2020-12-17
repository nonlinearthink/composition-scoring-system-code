package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.constant.ErrorDictionary;
import cn.edu.zucc.se2020g11.api.constant.LogCategory;
import cn.edu.zucc.se2020g11.api.dao.CommentEntityMapper;
import cn.edu.zucc.se2020g11.api.dao.ReCommentEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.CommentEntity;
import cn.edu.zucc.se2020g11.api.entity.SupportEntity;
import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService
{
    private final CommentEntityMapper commentEntityMapper;
    private final ReCommentEntityMapper reCommentEntityMapper;

    @Autowired(required = false)
    public CommentService(CommentEntityMapper commentEntityMapper, ReCommentEntityMapper reCommentEntityMapper)
    {
        this.commentEntityMapper = commentEntityMapper;
        this.reCommentEntityMapper = reCommentEntityMapper;
    }
    public List<CommentEntity> selectAllComments(int compositionId)
    {
        return commentEntityMapper.selectAllSelective(compositionId);
    }
    public int addComment(CommentEntity commentEntity)
    {
        commentEntityMapper.insert(commentEntity);
        return commentEntity.getCommentId();
    }
    public void deleteComment(int commentId)
    {
        int num = commentEntityMapper.deleteByPrimaryKey(commentId);
        if(num == 0){
            throw new BaseException(ErrorDictionary.NO_SUPPORT, LogCategory.BUSINESS);
        }
    }
}
