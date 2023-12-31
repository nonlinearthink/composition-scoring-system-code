package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.dao.*;
import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import cn.edu.zucc.se2020g11.api.model.ZoneModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 空间服务层
 *
 * @author Tuenity
 */
@Service
public class ZoneService
{

    private final UserEntityMapper userEntityMapper;

    @Autowired(required = false)
    public ZoneService(UserEntityMapper userEntityMapper)
    {
        this.userEntityMapper = userEntityMapper;
    }

    /**
     * 获取用户空间
     *
     * @param targetUsername 目标用户名
     * @return 空间模型
     */
    public ZoneModel selectZone(String targetUsername)
    {
        UserEntity userEntity = userEntityMapper.selectByPrimaryKey(targetUsername);
        ZoneModel zoneModel = new ZoneModel();
        zoneModel.setUsername(userEntity.getUsername());
        zoneModel.setNickname(userEntity.getNickname());
        zoneModel.setIsMale(userEntity.getIsMale());
        zoneModel.setSignature(userEntity.getSignature());
        zoneModel.setAvatarUrl(userEntity.getAvatarUrl());

        return zoneModel;
    }

}
