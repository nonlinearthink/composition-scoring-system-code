package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.CompositionReportEntity;

public interface CompositionReportEntityMapper {
    int deleteByPrimaryKey(Integer compositionReportId);

    int insert(CompositionReportEntity record);

    int insertSelective(CompositionReportEntity record);

    CompositionReportEntity selectByPrimaryKey(Integer compositionReportId);

    int updateByPrimaryKeySelective(CompositionReportEntity record);

    int updateByPrimaryKey(CompositionReportEntity record);
}