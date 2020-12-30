package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.constant.ErrorDictionary;
import cn.edu.zucc.se2020g11.api.constant.LogCategory;
import cn.edu.zucc.se2020g11.api.dao.FollowEntityMapper;
import cn.edu.zucc.se2020g11.api.dao.UserEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.FollowEntity;
import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import cn.edu.zucc.se2020g11.api.model.FollowCardModel;
import cn.edu.zucc.se2020g11.api.model.FollowModel;
import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FollowService
{

    private final FollowEntityMapper followEntityMapper;
    private final UserEntityMapper userEntityMapper;

    @Autowired(required = false)
    public FollowService(FollowEntityMapper followEntityMapper, UserEntityMapper userEntityMapper)
    {
        this.followEntityMapper = followEntityMapper;
        this.userEntityMapper = userEntityMapper;
    }
    public int addFollow(FollowEntity followEntity)
    {
        if(followEntity.getUsername().equals(followEntity.getTargetUsername())){
            throw new BaseException(ErrorDictionary.FOLLOW_ERROR, LogCategory.BUSINESS);
        } else if(followEntityMapper.selectByUsernameAndTargetUsername(followEntity).size() > 0){
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
    public List<UserEntity> findFollows(List<FollowEntity> followEntityList)
    {
        List<UserEntity>  userEntityList = new ArrayList<>();
        for (FollowEntity f : followEntityList) {
            userEntityList.add(userEntityMapper.selectByPrimaryKey(f.getTargetUsername()));
        }
        return userEntityList;
    }
    public List<UserEntity> findFollowers(List<FollowEntity> followEntityList)
    {
        List<UserEntity>  userEntityList = new ArrayList<>();
        for (FollowEntity f : followEntityList) {
            userEntityList.add(userEntityMapper.selectByPrimaryKey(f.getUsername()));
        }
        return userEntityList;
    }
    public Boolean findFollow(String username, String targetUsername)
    {
        FollowEntity followEntity = new FollowEntity();
        followEntity.setUsername(username);
        followEntity.setTargetUsername(targetUsername);
        List<FollowEntity> followEntityList = followEntityMapper.selectByUsernameAndTargetUsername(followEntity);
        return followEntityList.size() > 0;
    }
    public List<FollowModel> getFollowInfo(List<UserEntity>  userEntityList)
    {
        List<FollowModel>  followModelList = new ArrayList<>();
        for (UserEntity u : userEntityList) {
            FollowModel f = new FollowModel();
            f.setUsername(u.getUsername());
            f.setNickname(u.getNickname());
            f.setSignature(u.getSignature());
            f.setAvatarUrl(u.getAvatarUrl());
            followModelList.add(f);
        }
        return followModelList;
    }
    public void deleteFollow(FollowEntity followEntity)
    {
        int num = followEntityMapper.deleteByUsernameAndTargetUsername(followEntity);
        if(num == 0){
            throw new BaseException(ErrorDictionary.NO_FOLLOW, LogCategory.BUSINESS);
        }
    }
}
