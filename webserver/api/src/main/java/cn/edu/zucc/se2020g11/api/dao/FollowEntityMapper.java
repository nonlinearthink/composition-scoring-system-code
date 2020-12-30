package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.FollowEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FollowEntityMapper {
    int deleteByPrimaryKey(Integer followId);

    int deleteByUsernameAndTargetUsername(FollowEntity record);

    int insert(FollowEntity record);

    int insertSelective(FollowEntity record);

    FollowEntity selectByPrimaryKey(Integer followId);

    int updateByPrimaryKeySelective(FollowEntity record);

    int updateByPrimaryKey(FollowEntity record);

    List<FollowEntity> selectAllSelective(FollowEntity record);

    List<FollowEntity> selectAllSelectiveByUser(FollowEntity record);

    List<FollowEntity> selectByUsernameAndTargetUsername(FollowEntity record);
}