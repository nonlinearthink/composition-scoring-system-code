package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.dao.CompositionEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.CompositionEntity;
import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import cn.edu.zucc.se2020g11.api.model.CompositionCountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompositionService
{

    private final CompositionEntityMapper compositionEntityMapper;

    @Autowired(required = false)
    public CompositionService(CompositionEntityMapper compositionEntityMapper)
    {
        this.compositionEntityMapper = compositionEntityMapper;
    }
    public int addComposition(CompositionEntity compositionEntity)
    {
        compositionEntityMapper.insert(compositionEntity);
        return compositionEntity.getCompositionId();
    }
    public List<CompositionEntity> selectAllCompositions(UserEntity userEntity)
    {
        return compositionEntityMapper.selectAllSelective(userEntity);
    }
    public CompositionEntity selectCompositionById(Integer compositionId)
    {
        return compositionEntityMapper.selectByPrimaryKey(compositionId);
    }
    public void deleteComposition(Integer compositionId)
    {
        compositionEntityMapper.deleteByPrimaryKey(compositionId);
    }
    public void updateComposition(CompositionEntity compositionEntity, Integer compositionId)
    {
        compositionEntity.setCompositionId(compositionId);
        compositionEntityMapper.updateByPrimaryKeySelective(compositionEntity);
    }
    public void updateCompositionByValid(CompositionEntity compositionEntity, Integer compositionId)
    {
        compositionEntity.setCompositionId(compositionId);
        compositionEntityMapper.updateByValid(compositionEntity);
    }
    public CompositionCountModel selectCountByCompositionId(Integer compositionId){
        return compositionEntityMapper.selectCountByCompositionId(compositionId);
    }
}
