package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.CompositionReportEntity;
import cn.edu.zucc.se2020g11.api.model.ReportModel;

import java.util.List;

public interface CompositionReportEntityMapper {
    int deleteByPrimaryKey(Integer compositionReportId);

    int insert(CompositionReportEntity record);

    int insertSelective(CompositionReportEntity record);

    CompositionReportEntity selectByPrimaryKey(Integer compositionReportId);

    List<CompositionReportEntity> selectAll();

    List<ReportModel> selectAllReports();

    int updateByPrimaryKeySelective(CompositionReportEntity record);

    int updateByPrimaryKey(CompositionReportEntity record);
}