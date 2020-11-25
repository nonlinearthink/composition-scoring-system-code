package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.dao.CompositionEntityMapper;
import cn.edu.zucc.se2020g11.api.dao.DynamicEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.CompositionEntity;
import cn.edu.zucc.se2020g11.api.entity.DynamicEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DynamicService
{

    private final DynamicEntityMapper dynamicEntityMapper;

    @Autowired(required = false)
    public DynamicService(DynamicEntityMapper dynamicEntityMapper)
    {
        this.dynamicEntityMapper = dynamicEntityMapper;
    }
    public void insert(DynamicEntity dynamicEntity)
    {
        dynamicEntityMapper.insert(dynamicEntity);
    }

}
