package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.SpellErrorEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SpellErrorEntityMapper {
    int deleteByPrimaryKey(Integer spellErrorId);

    int insert(SpellErrorEntity record);

    int insertSelective(SpellErrorEntity record);

    SpellErrorEntity selectByPrimaryKey(Integer spellErrorId);

    int updateByPrimaryKeySelective(SpellErrorEntity record);

    int updateByPrimaryKey(SpellErrorEntity record);
}