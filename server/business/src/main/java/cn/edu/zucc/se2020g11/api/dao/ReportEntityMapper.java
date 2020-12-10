package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.ReportEntity;

public interface ReportEntityMapper {
    int deleteByPrimaryKey(Integer reportId);

    int insert(ReportEntity record);

    int insertSelective(ReportEntity record);

    ReportEntity selectByPrimaryKey(Integer reportId);

    int updateByPrimaryKeySelective(ReportEntity record);

    int updateByPrimaryKey(ReportEntity record);
}