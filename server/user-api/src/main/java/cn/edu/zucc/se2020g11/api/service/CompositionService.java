package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.dao.CompositionEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.CompositionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompositionService
{

    private final CompositionEntityMapper compositionEntityMapper;

    @Autowired(required = false)
    public CompositionService(CompositionEntityMapper compositionEntityMapper)
    {
        this.compositionEntityMapper = compositionEntityMapper;
    }
    public void insert(CompositionEntity compositionEntity)
    {
        compositionEntityMapper.insert(compositionEntity);
    }

}
