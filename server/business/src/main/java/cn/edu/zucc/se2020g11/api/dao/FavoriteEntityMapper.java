package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.FavoriteEntity;

public interface FavoriteEntityMapper {
    int deleteByPrimaryKey(Integer favotiteId);

    int insert(FavoriteEntity record);

    int insertSelective(FavoriteEntity record);

    FavoriteEntity selectByPrimaryKey(Integer favotiteId);

    int updateByPrimaryKeySelective(FavoriteEntity record);

    int updateByPrimaryKey(FavoriteEntity record);
}