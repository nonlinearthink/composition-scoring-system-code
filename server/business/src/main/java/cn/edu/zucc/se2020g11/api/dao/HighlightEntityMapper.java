package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.HighlightEntity;

public interface HighlightEntityMapper {
    int deleteByPrimaryKey(Integer highlightId);

    int insert(HighlightEntity record);

    int insertSelective(HighlightEntity record);

    HighlightEntity selectByPrimaryKey(Integer highlightId);

    int updateByPrimaryKeySelective(HighlightEntity record);

    int updateByPrimaryKey(HighlightEntity record);
}