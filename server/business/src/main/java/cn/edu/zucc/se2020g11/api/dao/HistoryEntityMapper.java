package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.HistoryEntity;

import java.util.List;

public interface HistoryEntityMapper {
    int deleteByPrimaryKey(Integer historyId);

    int insert(HistoryEntity record);

    int insertSelective(HistoryEntity record);

    HistoryEntity selectByPrimaryKey(Integer historyId);

    int updateByPrimaryKeySelective(HistoryEntity record);

    int updateByPrimaryKey(HistoryEntity record);

    List<HistoryEntity> selectHistoryByUser(String username);
}