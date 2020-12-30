package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.CommentEntity;
import cn.edu.zucc.se2020g11.api.model.CommentViewModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CommentEntityMapper {
    int deleteByPrimaryKey(Integer commentId);

    int deleteByCompositionId(Integer compositionId);

    int insert(CommentEntity record);

    int insertSelective(CommentEntity record);

    CommentEntity selectByPrimaryKey(Integer commentId);

    int updateByPrimaryKeySelective(CommentEntity record);

    int updateByPrimaryKey(CommentEntity record);

    int updateByStatus(CommentEntity record);

    List<CommentEntity> selectAllSelective(Integer compositionId);

    List<CommentEntity> selectAll();

    Integer countComment(Integer compositionId);

    List<CommentViewModel> selectCommentView(String targetUsername);
}