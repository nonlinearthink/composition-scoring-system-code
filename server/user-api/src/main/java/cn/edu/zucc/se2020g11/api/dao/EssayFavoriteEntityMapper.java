package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.EssayFavoriteEntity;

public interface EssayFavoriteEntityMapper {
    int deleteByPrimaryKey(Integer eFavoriteId);

    int insert(EssayFavoriteEntity record);

    int insertSelective(EssayFavoriteEntity record);

    EssayFavoriteEntity selectByPrimaryKey(Integer eFavoriteId);

    int updateByPrimaryKeySelective(EssayFavoriteEntity record);

    int updateByPrimaryKey(EssayFavoriteEntity record);
}