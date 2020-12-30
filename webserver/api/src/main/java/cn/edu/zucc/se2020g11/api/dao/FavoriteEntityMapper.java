package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.FavoriteEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FavoriteEntityMapper {
    int deleteByPrimaryKey(Integer favoriteId);

    int deleteByUsernameAndCompositionId(FavoriteEntity record);

    int deleteByCompositionId(Integer compositionId);

    int insert(FavoriteEntity record);

    int insertSelective(FavoriteEntity record);

    FavoriteEntity selectByPrimaryKey(Integer favoriteId);

    int updateByPrimaryKeySelective(FavoriteEntity record);

    int updateByPrimaryKey(FavoriteEntity record);

    List<FavoriteEntity> selectAllSelective(String username);

    List<FavoriteEntity> selectByUsernameAndCompositionId(FavoriteEntity record);
}