package cn.edu.zucc.se2020g11.api.service;


import cn.edu.zucc.se2020g11.api.dao.CompositionEntityMapper;
import cn.edu.zucc.se2020g11.api.dao.HistoryEntityMapper;
import cn.edu.zucc.se2020g11.api.dao.UserEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.CompositionEntity;
import cn.edu.zucc.se2020g11.api.entity.HistoryEntity;
import cn.edu.zucc.se2020g11.api.model.HistoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 历史记录服务层
 *
 * @author Tuenity
 */
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

    /**
     * 添加历史记录
     *
     * @param historyEntity 历史记录实体
     * @return 历史记录ID
     */
    public int addHistory(HistoryEntity historyEntity)
    {
        historyEntityMapper.insert(historyEntity);
        return historyEntity.getHistoryId();
    }

    /**
     * 获取用户历史记录
     *
     * @param username 用户名
     * @return 历史记录列表
     */
    public List<HistoryModel> selectHistoryByUser(String username)
    {
        List<HistoryEntity> historyEntityList = historyEntityMapper.selectHistoryByUser(username);
        historyEntityList.sort(((o1, o2) -> (int) (o2.getTime().getTime()-o1.getTime().getTime())));
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

    /**
     * 删除文章历史记录
     *
     * @param compositionId 文章ID
     * @return 是否删除成功
     */
    public int deleteHistoryByCompositionId(Integer compositionId)
    {
        return historyEntityMapper.deleteByCompositionId(compositionId);
    }
}
