package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.EssayHistoryEntity;

public interface EssayHistoryEntityMapper {
    int deleteByPrimaryKey(Integer eHistoryId);

    int insert(EssayHistoryEntity record);

    int insertSelective(EssayHistoryEntity record);

    EssayHistoryEntity selectByPrimaryKey(Integer eHistoryId);

    int updateByPrimaryKeySelective(EssayHistoryEntity record);

    int updateByPrimaryKey(EssayHistoryEntity record);
}