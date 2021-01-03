package cn.edu.zucc.se2020g11.api.service;


import cn.edu.zucc.se2020g11.api.dao.CompositionEntityMapper;
import cn.edu.zucc.se2020g11.api.dao.HistoryEntityMapper;
import cn.edu.zucc.se2020g11.api.dao.UserEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.CompositionEntity;
import cn.edu.zucc.se2020g11.api.entity.HistoryEntity;
import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import cn.edu.zucc.se2020g11.api.model.HistoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HistoryService
{
    private final HistoryEntityMapper historyEntityMapper;
    private final CompositionEntityMapper compositionEntityMapper;
    private final UserEntityMapper userEntityMapper;

    @Autowired(required = false)
    public HistoryService(HistoryEntityMapper historyEntityMapper, CompositionEntityMapper compositionEntityMapper, UserEntityMapper userEntityMapper)
    {
        this.historyEntityMapper = historyEntityMapper;
        this.compositionEntityMapper = compositionEntityMapper;
        this.userEntityMapper = userEntityMapper;
    }
    public int addHistory(HistoryEntity historyEntity)
    {
        historyEntityMapper.insert(historyEntity);
        return historyEntity.getHistoryId();
    }
    public List<HistoryModel> selectHistoryByUser(String username)
    {
        List<HistoryEntity> historyEntityList = historyEntityMapper.selectHistoryByUser(username);
        HashSet<Integer> cache = new HashSet<>();
        List<HistoryModel> historyModelList = new ArrayList<>();
        for(HistoryEntity h :historyEntityList){
            if(cache.add(h.getCompositionId())){
                HistoryModel historyModel = new HistoryModel();
                CompositionEntity compositionEntity = compositionEntityMapper.selectByPrimaryKey(h.getCompositionId());
                historyModel.setHistoryId(h.getHistoryId());
                historyModel.setNickname(userEntityMapper.selectByPrimaryKey(username).getNickname());
                historyModel.setTitle(compositionEntity.getTitle());
                historyModel.setCompositionId(compositionEntity.getCompositionId());
                historyModel.setCompositionBody(compositionEntity.getCompositionBody());
                historyModel.setTime(h.getTime());
                historyModelList.add(historyModel);
            }
        }
        return  historyModelList;
    }
    public int deleteHistoryByCompositionId(Integer compositionId)
    {
        return historyEntityMapper.deleteByCompositionId(compositionId);
    }
}
