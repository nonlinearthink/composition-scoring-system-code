package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.constant.ErrorDictionary;
import cn.edu.zucc.se2020g11.api.constant.LogCategory;
import cn.edu.zucc.se2020g11.api.dao.CommentEntityMapper;
import cn.edu.zucc.se2020g11.api.dao.CompositionEntityMapper;
import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

/**
 * 认证服务层
 *
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

    private final CompositionEntityMapper compositionEntityMapper;
    private final CommentEntityMapper commentEntityMapper;

    @Autowired(required = false)
    public PermissionService(CompositionEntityMapper compositionEntityMapper, CommentEntityMapper commentEntityMapper) {
        this.compositionEntityMapper = compositionEntityMapper;
        this.commentEntityMapper = commentEntityMapper;
    }

    /**
     * 认证作文
     *
     * @param username 用户名
     * @param compositionId 文章ID
     */
    public boolean validateComposition(String username, Integer compositionId) throws BaseException
    {
        if (!username.equals(compositionEntityMapper.selectByPrimaryKey(compositionId).getUsername())) {
            throw new BaseException(ErrorDictionary.NO_PERMISSION, LogCategory.BUSINESS);
        }
        logger.info("文章权限认证成功");
        return true;
    }

    /**
     * 认证评论
     *
     * @param username 用户名
     * @param commentId 评论ID
     */
    public boolean validateComment(String username, Integer commentId) throws BaseException
    {
        if (!username.equals(commentEntityMapper.selectByPrimaryKey(commentId).getUsername())) {
            throw new BaseException(ErrorDictionary.NO_PERMISSION, LogCategory.BUSINESS);
        }
        logger.info("评论权限认证成功");
        return true;
    }
}
