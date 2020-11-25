package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.AdvertiserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdvertiserEntityMapper {
    int deleteByPrimaryKey(String advertiserName);

    int insert(AdvertiserEntity record);

    int insertSelective(AdvertiserEntity record);

    AdvertiserEntity selectByPrimaryKey(String advertiserName);

    int updateByPrimaryKeySelective(AdvertiserEntity record);

    int updateByPrimaryKey(AdvertiserEntity record);
}