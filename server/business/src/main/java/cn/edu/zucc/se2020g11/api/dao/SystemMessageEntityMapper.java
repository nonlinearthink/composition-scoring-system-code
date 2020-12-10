package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.SystemMessageEntity;

public interface SystemMessageEntityMapper {
    int deleteByPrimaryKey(Integer sMessageId);

    int insert(SystemMessageEntity record);

    int insertSelective(SystemMessageEntity record);

    SystemMessageEntity selectByPrimaryKey(Integer sMessageId);

    int updateByPrimaryKeySelective(SystemMessageEntity record);

    int updateByPrimaryKey(SystemMessageEntity record);
}