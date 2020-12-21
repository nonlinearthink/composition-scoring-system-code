package cn.edu.zucc.se2020g11.api.service;


import cn.edu.zucc.se2020g11.api.dao.CompositionEntityMapper;
import cn.edu.zucc.se2020g11.api.dao.HistoryEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.CompositionEntity;
import cn.edu.zucc.se2020g11.api.entity.HistoryEntity;
import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService
{
    private final HistoryEntityMapper historyEntityMapper;

    @Autowired(required = false)
    public HistoryService(HistoryEntityMapper historyEntityMapper)
    {
        this.historyEntityMapper = historyEntityMapper;
    }
    public int addHistory(HistoryEntity historyEntity)
    {
        historyEntityMapper.insert(historyEntity);
        return historyEntity.getHistoryId();
    }
    public List<HistoryEntity> selectHistoryByUser(String username)
    {
        return historyEntityMapper.selectHistoryByUser(username);
    }

}
