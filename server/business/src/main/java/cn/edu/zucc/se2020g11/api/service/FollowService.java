package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.constant.ErrorDictionary;
import cn.edu.zucc.se2020g11.api.constant.LogCategory;
import cn.edu.zucc.se2020g11.api.dao.FollowEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.FollowEntity;
import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
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
        if(followEntity.getUsername().equals(followEntity.getTargetUsername())){
            throw new BaseException(ErrorDictionary.FOLLOW_ERROR, LogCategory.BUSINESS);
        } else if(followEntityMapper.selectByUsername(followEntity).size() > 0){
            throw new BaseException(ErrorDictionary.REPEAT_FOLLOW, LogCategory.BUSINESS);
        }
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
    public void deleteFollow(FollowEntity followEntity)
    {
        int num = followEntityMapper.deleteByUsername(followEntity);
        if(num == 0){
            throw new BaseException(ErrorDictionary.NO_FOLLOW, LogCategory.BUSINESS);
        }
    }
}
