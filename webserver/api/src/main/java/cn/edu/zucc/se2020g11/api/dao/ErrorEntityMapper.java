package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.ErrorEntity;
import org.springframework.stereotype.Component;

@Component
public interface ErrorEntityMapper {
    int deleteByPrimaryKey(Integer errorId);

    int deleteByCompositionId(Integer errorId);

    int insert(ErrorEntity record);

    int insertSelective(ErrorEntity record);

    ErrorEntity selectByPrimaryKey(Integer errorId);

    ErrorEntity selectByCompositionIdAndErrorType(Integer compositionId, String errorType);

    int updateByPrimaryKeySelective(ErrorEntity record);

    int updateByPrimaryKey(ErrorEntity record);
}