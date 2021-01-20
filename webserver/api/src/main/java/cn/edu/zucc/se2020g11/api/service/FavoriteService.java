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

/**
 * 收藏服务层
 *
 * @author Tuenity
 */
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

    /**
     * 添加收藏
     *
     * @param favoriteEntity 收藏实体
     * @return 收藏ID
     */
    public int addFavorite(FavoriteEntity favoriteEntity)
    {
        if(favoriteEntityMapper.selectByUsernameAndCompositionId(favoriteEntity).size() > 0){
            // 重复收藏
            throw new BaseException(ErrorDictionary.REPEAT_FAVORITE, LogCategory.BUSINESS);
        }
        favoriteEntityMapper.insert(favoriteEntity);
        return favoriteEntity.getFavoriteId();
    }

    /**
     * 获取用户全部收藏
     *
     * @param username 用户名
     * @return 收藏列表
     */
    public List<FavoriteEntity> selectAllFavorites(String username)
    {
        return favoriteEntityMapper.selectAllSelective(username);
    }

    /**
     * 查找收藏文章
     *
     * @param favoriteEntityList 收藏列表
     * @return 文章列表
     */
    public List<CompositionEntity> findFavoriteComposition(List<FavoriteEntity> favoriteEntityList)
    {
        List<CompositionEntity>  compositionEntityList = new ArrayList<>();
        for (FavoriteEntity f : favoriteEntityList) {
            compositionEntityList.add(compositionEntityMapper.selectByPrimaryKey(f.getCompositionId()));
        }
        return compositionEntityList;
    }

    /**
     * 判断是否收藏
     *
     * @param username 用户名
     * @param compositionId 文章ID
     * @return 是否收藏
     */
    public Boolean findFavorite(String username, Integer compositionId)
    {
        FavoriteEntity favoriteEntity = new FavoriteEntity();
        favoriteEntity.setUsername(username);
        favoriteEntity.setCompositionId(compositionId);
        List<FavoriteEntity> favoriteEntityList = favoriteEntityMapper.selectByUsernameAndCompositionId(favoriteEntity);
        return favoriteEntityList.size() > 0;
    }

    /**
     * 删除收集
     *
     * @param favoriteEntity 收藏实体
     * @return 是否删除成功
     */
    public int deleteFavorite(FavoriteEntity favoriteEntity)
    {
        int num = favoriteEntityMapper.deleteByUsernameAndCompositionId(favoriteEntity);
        if(num == 0){
            // 未收藏
            throw new BaseException(ErrorDictionary.NO_FAVORITE, LogCategory.BUSINESS);
        }
        return num;
    }

    /**
     * 删除文章收藏
     *
     * @param compositionId 文章ID
     * @return 是否删除成功过
     */
    public int deleteFavoriteByCompositionId(Integer compositionId)
    {
        return favoriteEntityMapper.deleteByCompositionId(compositionId);
    }
}
