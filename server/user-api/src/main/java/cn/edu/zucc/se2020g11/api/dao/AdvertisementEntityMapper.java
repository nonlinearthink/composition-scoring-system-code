package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.AdvertisementEntity;

public interface AdvertisementEntityMapper {
    int deleteByPrimaryKey(Integer advertisementId);

    int insert(AdvertisementEntity record);

    int insertSelective(AdvertisementEntity record);

    AdvertisementEntity selectByPrimaryKey(Integer advertisementId);

    int updateByPrimaryKeySelective(AdvertisementEntity record);

    int updateByPrimaryKey(AdvertisementEntity record);
}