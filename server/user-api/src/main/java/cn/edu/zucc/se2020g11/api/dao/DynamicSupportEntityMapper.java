package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.DynamicSupportEntity;

public interface DynamicSupportEntityMapper {
    int deleteByPrimaryKey(Integer dsupportId2);

    int insert(DynamicSupportEntity record);

    int insertSelective(DynamicSupportEntity record);

    DynamicSupportEntity selectByPrimaryKey(Integer dsupportId2);

    int updateByPrimaryKeySelective(DynamicSupportEntity record);

    int updateByPrimaryKey(DynamicSupportEntity record);
}