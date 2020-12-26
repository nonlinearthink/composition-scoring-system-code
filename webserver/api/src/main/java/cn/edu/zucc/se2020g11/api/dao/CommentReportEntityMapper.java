package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.CommentReportEntity;

import java.util.List;

public interface CommentReportEntityMapper {
    int deleteByPrimaryKey(Integer commentReportId);

    int insert(CommentReportEntity record);

    int insertSelective(CommentReportEntity record);

    CommentReportEntity selectByPrimaryKey(Integer commentReportId);

    List<CommentReportEntity> selectAll();

    int updateByPrimaryKeySelective(CommentReportEntity record);

    int updateByPrimaryKey(CommentReportEntity record);
}