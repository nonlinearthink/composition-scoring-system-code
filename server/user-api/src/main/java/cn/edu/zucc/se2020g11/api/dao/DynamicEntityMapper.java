package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.DynamicEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DynamicEntityMapper {
    int deleteByPrimaryKey(Integer dynamicId);

    int insert(DynamicEntity record);

    int insertSelective(DynamicEntity record);

    DynamicEntity selectByPrimaryKey(Integer dynamicId);

    int updateByPrimaryKeySelective(DynamicEntity record);

    int updateByPrimaryKey(DynamicEntity record);
}