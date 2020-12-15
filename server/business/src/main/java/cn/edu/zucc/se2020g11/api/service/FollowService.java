package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.dao.FollowEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.FollowEntity;
import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService
{

    private final FollowEntityMapper followEntityMapper;

    @Autowired(required = false)
    public FollowService(FollowEntityMapper followEntityMapper)
    {
        this.followEntityMapper = followEntityMapper;
    }
    public int addFollow(FollowEntity followEntity)
    {
        followEntityMapper.insert(followEntity);
        return followEntity.getFollowId();
    }
    public List<FollowEntity> selectAllFollows(FollowEntity followEntity)
    {
        return followEntityMapper.selectAllSelective(followEntity);
    }
    public List<FollowEntity> selectAllFollowers(FollowEntity followEntity)
    {
        return followEntityMapper.selectAllSelectiveByUser(followEntity);
    }
}
