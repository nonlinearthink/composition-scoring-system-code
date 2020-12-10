package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.FollowEntity;

public interface FollowEntityMapper {
    int deleteByPrimaryKey(Integer followId);

    int insert(FollowEntity record);

    int insertSelective(FollowEntity record);

    FollowEntity selectByPrimaryKey(Integer followId);

    int updateByPrimaryKeySelective(FollowEntity record);

    int updateByPrimaryKey(FollowEntity record);
}