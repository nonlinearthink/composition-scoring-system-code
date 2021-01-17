package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import cn.edu.zucc.se2020g11.api.model.SupportViewModel;
import cn.edu.zucc.se2020g11.api.model.UsernameCardModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserEntityMapper {
    int deleteByPrimaryKey(String username);

    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    UserEntity selectByPrimaryKey(String username);

    UserEntity selectByEmail(String email);

    UserEntity selectByUsernameAndEmail(String username, String email);

    List<String> selectUserByNickname(String key);

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByPrimaryKey(UserEntity record);

    int updateUser(UserEntity record);

    int selectSum();

    List<UsernameCardModel> selectUserView(String key);
}