package cn.edu.zucc.se2020g11.apiserver.dao;

import cn.edu.zucc.se2020g11.apiserver.bean.User;

public interface UserMapper {
    int deleteByPrimaryKey(String userName);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String userName);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}