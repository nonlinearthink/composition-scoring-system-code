package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.dao.ErrorEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.ErrorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Tuenity
 */
@Service
public class ErrorService
{
    private final ErrorEntityMapper errorEntityMapper;

    @Autowired(required = false)
    public ErrorService(ErrorEntityMapper errorEntityMapper)
    {
        this.errorEntityMapper = errorEntityMapper;
    }
    public int addError(ErrorEntity errorEntity)
    {
        errorEntityMapper.insertSelective(errorEntity);
        return errorEntity.getCompositionId();
    }
    public ErrorEntity selectError(Integer compositionId, String errorType)
    {
        return errorEntityMapper.selectByCompositionIdAndErrorType(compositionId, errorType);
    }
    public int deleteErrorByCompositionId(Integer compositionId)
    {
        return errorEntityMapper.deleteByCompositionId(compositionId);
    }
}
