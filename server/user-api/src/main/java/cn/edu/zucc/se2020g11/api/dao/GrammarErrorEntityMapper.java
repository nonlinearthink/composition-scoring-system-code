package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.GrammarErrorEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GrammarErrorEntityMapper {
    int deleteByPrimaryKey(Integer grammerErrorId);

    int insert(GrammarErrorEntity record);

    int insertSelective(GrammarErrorEntity record);

    GrammarErrorEntity selectByPrimaryKey(Integer grammerErrorId);

    int updateByPrimaryKeySelective(GrammarErrorEntity record);

    int updateByPrimaryKey(GrammarErrorEntity record);
}