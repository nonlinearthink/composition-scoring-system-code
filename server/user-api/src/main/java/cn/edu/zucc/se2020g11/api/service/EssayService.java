package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.dao.EssayEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.EssayEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EssayService
{

    private final EssayEntityMapper essayEntityMapper;

    @Autowired(required = false)
    public EssayService(EssayEntityMapper essayEntityMapper)
    {
        this.essayEntityMapper = essayEntityMapper;
    }
    public void insert(EssayEntity essayEntity)
    {
        essayEntityMapper.insert(essayEntity);
    }

}
