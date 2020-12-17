package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.FavoriteEntity;

import java.util.List;

public interface FavoriteEntityMapper {
    int deleteByPrimaryKey(Integer favoriteId);

    int deleteByCompositionId(FavoriteEntity record);

    int insert(FavoriteEntity record);

    int insertSelective(FavoriteEntity record);

    FavoriteEntity selectByPrimaryKey(Integer favoriteId);

    int updateByPrimaryKeySelective(FavoriteEntity record);

    int updateByPrimaryKey(FavoriteEntity record);

    List<FavoriteEntity> selectAllSelective(String username);

    List<FavoriteEntity> selectByUsername(FavoriteEntity record);
}