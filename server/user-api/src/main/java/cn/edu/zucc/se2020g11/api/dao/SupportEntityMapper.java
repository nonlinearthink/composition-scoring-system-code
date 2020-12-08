package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.SupportEntity;

public interface SupportEntityMapper {
    int deleteByPrimaryKey(Integer supportId);

    int insert(SupportEntity record);

    int insertSelective(SupportEntity record);

    SupportEntity selectByPrimaryKey(Integer supportId);

    int updateByPrimaryKeySelective(SupportEntity record);

    int updateByPrimaryKey(SupportEntity record);
}