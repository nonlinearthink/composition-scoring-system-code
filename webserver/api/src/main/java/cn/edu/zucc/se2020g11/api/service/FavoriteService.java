package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.constant.ErrorDictionary;
import cn.edu.zucc.se2020g11.api.constant.LogCategory;
import cn.edu.zucc.se2020g11.api.dao.CompositionEntityMapper;
import cn.edu.zucc.se2020g11.api.dao.FavoriteEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.CompositionEntity;
import cn.edu.zucc.se2020g11.api.entity.FavoriteEntity;
import cn.edu.zucc.se2020g11.api.entity.SupportEntity;
import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteService
{
    private final FavoriteEntityMapper favoriteEntityMapper;
    private final CompositionEntityMapper compositionEntityMapper;

    @Autowired(required = false)
    public FavoriteService(FavoriteEntityMapper favoriteEntityMapper, CompositionEntityMapper compositionEntityMapper)
    {
        this.favoriteEntityMapper = favoriteEntityMapper;
        this.compositionEntityMapper = compositionEntityMapper;
    }
    public int addFavorite(FavoriteEntity favoriteEntity)
    {
        if(favoriteEntityMapper.selectByUsernameAndCompositionId(favoriteEntity).size() > 0){
            throw new BaseException(ErrorDictionary.REPEAT_FAVORITE, LogCategory.BUSINESS);
        }
        favoriteEntityMapper.insert(favoriteEntity);
        return favoriteEntity.getFavoriteId();
    }
    public List<FavoriteEntity> selectAllFavorites(String username)
    {
        return favoriteEntityMapper.selectAllSelective(username);
    }
    public List<CompositionEntity> findFavoriteComposition(List<FavoriteEntity> favoriteEntityList)
    {
        List<CompositionEntity>  compositionEntityList = new ArrayList<>();
        for (FavoriteEntity f : favoriteEntityList) {
            compositionEntityList.add(compositionEntityMapper.selectByPrimaryKey(f.getCompositionId()));
        }
        return compositionEntityList;
    }
    public Boolean findFavorite(String username, Integer compositionId)
    {
        FavoriteEntity favoriteEntity = new FavoriteEntity();
        favoriteEntity.setUsername(username);
        favoriteEntity.setCompositionId(compositionId);
        List<FavoriteEntity> favoriteEntityList = favoriteEntityMapper.selectByUsernameAndCompositionId(favoriteEntity);
        return favoriteEntityList.size() > 0;
    }
    public int deleteFavorite(FavoriteEntity favoriteEntity)
    {
        int num = favoriteEntityMapper.deleteByUsernameAndCompositionId(favoriteEntity);
        if(num == 0){
            throw new BaseException(ErrorDictionary.NO_FAVORITE, LogCategory.BUSINESS);
        }
        return num;
    }
    public int deleteFavoriteByCompositionId(Integer compositionId)
    {
        return favoriteEntityMapper.deleteByCompositionId(compositionId);
    }
}
