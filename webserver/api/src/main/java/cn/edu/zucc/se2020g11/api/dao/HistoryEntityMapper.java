package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.HistoryEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public interface HistoryEntityMapper {
    int deleteByPrimaryKey(Integer historyId);

    int deleteByCompositionId(Integer historyId);

    int insert(HistoryEntity record);

    int insertSelective(HistoryEntity record);

    HistoryEntity selectByPrimaryKey(Integer historyId);

    int updateByPrimaryKeySelective(HistoryEntity record);

    int updateByPrimaryKey(HistoryEntity record);

    List<HistoryEntity> selectHistoryByUser(String username);

    Integer countHistory(Integer compositionId);

    List<HashMap<String, Object>> selectHistory();
}