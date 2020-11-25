package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.EssaySupport;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EssaySupportMapper {
    int deleteByPrimaryKey(Integer eSupportId);

    int insert(EssaySupport record);

    int insertSelective(EssaySupport record);

    EssaySupport selectByPrimaryKey(Integer eSupportId);

    int updateByPrimaryKeySelective(EssaySupport record);

    int updateByPrimaryKey(EssaySupport record);
}