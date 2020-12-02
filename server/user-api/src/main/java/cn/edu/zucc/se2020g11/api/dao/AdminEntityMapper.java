package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.AdminEntity;

public interface AdminEntityMapper {
    int deleteByPrimaryKey(String adminName);

    int insert(AdminEntity record);

    int insertSelective(AdminEntity record);

    AdminEntity selectByPrimaryKey(String adminName);

    int updateByPrimaryKeySelective(AdminEntity record);

    int updateByPrimaryKey(AdminEntity record);
}