package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.CompositionEntity;
import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import cn.edu.zucc.se2020g11.api.model.CompositionCountModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CompositionEntityMapper {
    int deleteByPrimaryKey(Integer compositionId);

    int insert(CompositionEntity record);

    int insertSelective(CompositionEntity record);

    CompositionEntity selectByPrimaryKey(Integer compositionId);

    List<CompositionEntity> selectByUsername(String username);

    int updateByPrimaryKeySelective(CompositionEntity record);

    int updateByPrimaryKey(CompositionEntity record);

    int updateByValid(CompositionEntity record);

    List<CompositionEntity> selectAllSelective(UserEntity record);

    List<CompositionEntity> selectFollow(String username);

    List<CompositionEntity> selectNew();

    List<CompositionCountModel> selectHot();

    CompositionCountModel selectCountByCompositionId(Integer compositionId);

    int selectSum();

    int selectSumByToday();
}