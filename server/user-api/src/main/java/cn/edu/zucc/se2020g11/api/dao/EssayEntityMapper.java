package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.EssayEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EssayEntityMapper {
    int deleteByPrimaryKey(Integer essayId);

    int insert(EssayEntity record);

    int insertSelective(EssayEntity record);

    EssayEntity selectByPrimaryKey(Integer essayId);

    int updateByPrimaryKeySelective(EssayEntity record);

    int updateByPrimaryKey(EssayEntity record);
}