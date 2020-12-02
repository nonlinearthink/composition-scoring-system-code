package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.CompositionEntity;

public interface CompositionEntityMapper {
    int deleteByPrimaryKey(Integer compositionId);

    int insert(CompositionEntity record);

    int insertSelective(CompositionEntity record);

    CompositionEntity selectByPrimaryKey(Integer compositionId);

    int updateByPrimaryKeySelective(CompositionEntity record);

    int updateByPrimaryKey(CompositionEntity record);
}