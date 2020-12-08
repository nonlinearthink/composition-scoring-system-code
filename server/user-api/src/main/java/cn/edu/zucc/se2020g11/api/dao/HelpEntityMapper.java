package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.HelpEntity;

public interface HelpEntityMapper {
    int deleteByPrimaryKey(Integer helpId);

    int insert(HelpEntity record);

    int insertSelective(HelpEntity record);

    HelpEntity selectByPrimaryKey(Integer helpId);

    int updateByPrimaryKeySelective(HelpEntity record);

    int updateByPrimaryKey(HelpEntity record);
}