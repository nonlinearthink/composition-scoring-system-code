package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.constant.ErrorDictionary;
import cn.edu.zucc.se2020g11.api.constant.LogCategory;
import cn.edu.zucc.se2020g11.api.dao.CompositionEntityMapper;
import cn.edu.zucc.se2020g11.api.dao.UserEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.CompositionEntity;
import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

/**
 * @author nonlinearthink
 */
@Service
@EnableAsync
public class PermissionService
{
    /**
     * 日志记录器
     */
    private final Logger logger = LogManager.getLogger(LogCategory.BUSINESS.getPosition());

    private final UserEntityMapper userEntityMapper;
    private final CompositionEntityMapper compositionEntityMapper;

    @Autowired(required = false)
    public PermissionService(UserEntityMapper userEntityMapper, CompositionEntityMapper compositionEntityMapper) {
        this.userEntityMapper = userEntityMapper;
        this.compositionEntityMapper = compositionEntityMapper;
    }

    public void validateComposition(String username, Integer compositionId) throws BaseException
    {
        if (!username.equals(compositionEntityMapper.selectByPrimaryKey(compositionId).getUsername())) {
            throw new BaseException(ErrorDictionary.NO_PERMISSION, LogCategory.BUSINESS);
        }
        logger.info("文章权限认证成功");
    }
}
