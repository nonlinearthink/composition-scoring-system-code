package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.CompositionSupportEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompositionSupportEntityMapper {
    int deleteByPrimaryKey(Integer dsupportId3);

    int insert(CompositionSupportEntity record);

    int insertSelective(CompositionSupportEntity record);

    CompositionSupportEntity selectByPrimaryKey(Integer dsupportId3);

    int updateByPrimaryKeySelective(CompositionSupportEntity record);

    int updateByPrimaryKey(CompositionSupportEntity record);
}