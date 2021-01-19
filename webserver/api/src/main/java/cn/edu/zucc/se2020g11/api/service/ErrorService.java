package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.dao.ErrorEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.ErrorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 错误服务层
 *
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

    /**
     * 添加错误
     *
     * @param errorEntity 错误实体
     * @return 错误ID
     */
    public int addError(ErrorEntity errorEntity)
    {
        errorEntityMapper.insertSelective(errorEntity);
        return errorEntity.getCompositionId();
    }

    /**
     * 获取错误
     *
     * @param compositionId 作文ID
     * @param errorType 作文类型
     * @return 错误实体
     */
    public ErrorEntity selectError(Integer compositionId, String errorType)
    {
        return errorEntityMapper.selectByCompositionIdAndErrorType(compositionId, errorType);
    }

    /**
     * 删除作文错误
     *
     * @param compositionId 作文ID
     * @return 是否删除成功
     */
    public int deleteErrorByCompositionId(Integer compositionId)
    {
        return errorEntityMapper.deleteByCompositionId(compositionId);
    }
}
