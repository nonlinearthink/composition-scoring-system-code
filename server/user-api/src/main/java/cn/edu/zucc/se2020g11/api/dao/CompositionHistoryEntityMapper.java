package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.CompositionHistoryEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompositionHistoryEntityMapper {
    int deleteByPrimaryKey(Integer cHistoryId);

    int insert(CompositionHistoryEntity record);

    int insertSelective(CompositionHistoryEntity record);

    CompositionHistoryEntity selectByPrimaryKey(Integer cHistoryId);

    int updateByPrimaryKeySelective(CompositionHistoryEntity record);

    int updateByPrimaryKey(CompositionHistoryEntity record);
}