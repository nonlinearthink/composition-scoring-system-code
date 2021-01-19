package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.constant.ErrorDictionary;
import cn.edu.zucc.se2020g11.api.constant.LogCategory;
import cn.edu.zucc.se2020g11.api.dao.FollowEntityMapper;
import cn.edu.zucc.se2020g11.api.dao.UserEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.FollowEntity;
import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import cn.edu.zucc.se2020g11.api.model.FollowModel;
import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 关注服务层
 *
 * @author Tuenity
 */
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

    /**
     * 添加关注
     *
     * @param followEntity 关注实体
     * @return 关注ID
     */
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

    /**
     * 获取我的关注
     *
     * @param followEntity 关注实体
     * @return 我的关注列表
     */
    public List<FollowEntity> selectAllFollows(FollowEntity followEntity)
    {
        return followEntityMapper.selectAllSelective(followEntity);
    }

    /**
     * 获取关注我的
     *
     * @param followEntity 关注实体
     * @return 关注我的列表
     */
    public List<FollowEntity> selectAllFollowers(FollowEntity followEntity)
    {
        return followEntityMapper.selectAllSelectiveByUser(followEntity);
    }

    /**
     * 查找关注的用户
     *
     * @param followEntityList 关注列表
     * @return 用户列表
     */
    public List<UserEntity> findFollows(List<FollowEntity> followEntityList)
    {
        List<UserEntity>  userEntityList = new ArrayList<>();
        for (FollowEntity f : followEntityList) {
            userEntityList.add(userEntityMapper.selectByPrimaryKey(f.getTargetUsername()));
        }
        return userEntityList;
    }

    /**
     * 查找关注我的用户
     *
     * @param followEntityList 关注列表
     * @return 用户列表
     */
    public List<UserEntity> findFollowers(List<FollowEntity> followEntityList)
    {
        List<UserEntity>  userEntityList = new ArrayList<>();
        for (FollowEntity f : followEntityList) {
            userEntityList.add(userEntityMapper.selectByPrimaryKey(f.getUsername()));
        }
        return userEntityList;
    }

    /**
     * 判断是否关注
     *
     * @param username 用户
     * @param targetUsername 目标用户
     * @return 是否关注
     */
    public Boolean findFollow(String username, String targetUsername)
    {
        FollowEntity followEntity = new FollowEntity();
        followEntity.setUsername(username);
        followEntity.setTargetUsername(targetUsername);
        List<FollowEntity> followEntityList = followEntityMapper.selectByUsernameAndTargetUsername(followEntity);
        return followEntityList.size() > 0;
    }

    /**
     * 获取关注信息
     *
     * @param userEntityList 用户列表
     * @return 关注模型
     */
    public List<FollowModel> getFollowInfo(List<UserEntity> userEntityList)
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

    /**
     * 删除关注
     *
     * @param followEntity 关注实体
     * @return 是否删除成功
     */
    public int deleteFollow(FollowEntity followEntity)
    {
        int num = followEntityMapper.deleteByUsernameAndTargetUsername(followEntity);
        if(num == 0){
            throw new BaseException(ErrorDictionary.NO_FOLLOW, LogCategory.BUSINESS);
        }
        return num;
    }
}
