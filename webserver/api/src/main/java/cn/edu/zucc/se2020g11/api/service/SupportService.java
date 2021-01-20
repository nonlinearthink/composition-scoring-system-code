package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.constant.ErrorDictionary;
import cn.edu.zucc.se2020g11.api.constant.LogCategory;
import cn.edu.zucc.se2020g11.api.dao.CompositionEntityMapper;
import cn.edu.zucc.se2020g11.api.dao.SupportEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.CompositionEntity;
import cn.edu.zucc.se2020g11.api.entity.SupportEntity;
import cn.edu.zucc.se2020g11.api.model.SupportViewModel;
import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 点赞服务层
 *
 * @author Tuenity
 */
@Service
public class SupportService
{

    private final SupportEntityMapper supportEntityMapper;
    private final CompositionEntityMapper compositionEntityMapper;

    @Autowired(required = false)
    public SupportService(SupportEntityMapper supportEntityMapper, CompositionEntityMapper compositionEntityMapper)
    {
        this.supportEntityMapper = supportEntityMapper;
        this.compositionEntityMapper = compositionEntityMapper;
    }

    /**
     * 添加点赞
     *
     * @param supportEntity 点赞实体
     * @return 点赞ID
     */
    public int addSupport(SupportEntity supportEntity)
    {
        if(supportEntityMapper.selectByUsernameAndCompositionId(supportEntity).size() > 0){
            // 重复点赞
            throw new BaseException(ErrorDictionary.REPEAT_SUPPORT, LogCategory.BUSINESS);
        }
        supportEntityMapper.insert(supportEntity);
        return supportEntity.getSupportId();
    }

    /**
     * 获取用户点赞
     *
     * @param username 用户名
     * @return 点赞列表
     */
    public List<SupportEntity> selectAllSupports(String username)
    {
        return supportEntityMapper.selectAllSelective(username);
    }

    /**
     * 获取用户点赞视图
     *
     * @param targetUsername 目标用户名
     * @return 点赞视图列表
     */
    public List<SupportViewModel> selectSupportView(String targetUsername)
    {
        return supportEntityMapper.selectSupportView(targetUsername);
    }

    /**
     * 获取点赞文章
     *
     * @param supportEntityList 点赞列表
     * @return 文章列表
     */
    public List<CompositionEntity> findSupportedComposition(List<SupportEntity> supportEntityList)
    {
        List<CompositionEntity>  compositionEntityList = new ArrayList<>();
        for (SupportEntity s : supportEntityList) {
            compositionEntityList.add(compositionEntityMapper.selectByPrimaryKey(s.getCompositionId()));
        }
        return compositionEntityList;
    }

    /**
     * 判断是否点赞
     *
     * @param username 用户名
     * @param compositionId 文章ID
     * @return 是否点赞
     */
    public Boolean findSupport(String username, Integer compositionId)
    {
        SupportEntity supportEntity = new SupportEntity();
        supportEntity.setUsername(username);
        supportEntity.setCompositionId(compositionId);
        List<SupportEntity> supportEntityList = supportEntityMapper.selectByUsernameAndCompositionId(supportEntity);
        return supportEntityList.size() > 0;
    }

    /**
     * 删除点赞
     *
     * @param supportEntity 点赞实体
     * @return 是否删除成功
     */
    public int deleteSupport(SupportEntity supportEntity)
    {
        int num = supportEntityMapper.deleteByUsernameAndCompositionId(supportEntity);
        if(num == 0){
            throw new BaseException(ErrorDictionary.NO_SUPPORT, LogCategory.BUSINESS);
        }
        return num;
    }

    /**
     * 删除文章点赞
     *
     * @param compositionId 文章ID
     * @return 是否删除成功
     */
    public int deleteSupportByCompositionId(Integer compositionId)
    {
        return supportEntityMapper.deleteByCompositionId(compositionId);
    }

}
