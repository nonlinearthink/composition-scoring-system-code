package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.FollowEntity;
import cn.edu.zucc.se2020g11.api.entity.FollowEntityKey;

public interface FollowEntityMapper {
    int deleteByPrimaryKey(FollowEntityKey key);

    int insert(FollowEntity record);

    int insertSelective(FollowEntity record);

    FollowEntity selectByPrimaryKey(FollowEntityKey key);

    int updateByPrimaryKeySelective(FollowEntity record);

    int updateByPrimaryKey(FollowEntity record);
}