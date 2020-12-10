package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.ErrorEntity;

public interface ErrorEntityMapper {
    int deleteByPrimaryKey(Integer errorId);

    int insert(ErrorEntity record);

    int insertSelective(ErrorEntity record);

    ErrorEntity selectByPrimaryKey(Integer errorId);

    int updateByPrimaryKeySelective(ErrorEntity record);

    int updateByPrimaryKey(ErrorEntity record);
}