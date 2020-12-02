package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.CompositionFavoriteEntity;

public interface CompositionFavoriteEntityMapper {
    int deleteByPrimaryKey(Integer cFavotiteId);

    int insert(CompositionFavoriteEntity record);

    int insertSelective(CompositionFavoriteEntity record);

    CompositionFavoriteEntity selectByPrimaryKey(Integer cFavotiteId);

    int updateByPrimaryKeySelective(CompositionFavoriteEntity record);

    int updateByPrimaryKey(CompositionFavoriteEntity record);
}