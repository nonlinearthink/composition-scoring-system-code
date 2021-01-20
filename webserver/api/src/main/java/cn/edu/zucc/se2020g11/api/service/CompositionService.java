package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.constant.LogCategory;
import cn.edu.zucc.se2020g11.api.dao.CompositionEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.CompositionEntity;
import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import cn.edu.zucc.se2020g11.api.model.CompositionCountModel;
import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章服务层
 *
 * @author Tuenity
 */
@Service
public class CompositionService
{

    private final Logger logger = LogManager.getLogger(LogCategory.SYSTEM);

    private final CompositionEntityMapper compositionEntityMapper;

    @Autowired(required = false)
    public CompositionService(CompositionEntityMapper compositionEntityMapper)
    {
        this.compositionEntityMapper = compositionEntityMapper;
    }

    /**
     * 添加作文
     *
     * @param compositionEntity 作文实体
     * @return 作文ID
     */
    public int addComposition(CompositionEntity compositionEntity)
    {
        compositionEntity.setValid(0);
        compositionEntityMapper.insert(compositionEntity);
        return compositionEntity.getCompositionId();
    }

    /**
     * 获取所有文章
     *
     * @param userEntity 用户实体
     * @return 文章列表
     */
    public List<CompositionEntity> selectAllCompositions(UserEntity userEntity)
    {
        return compositionEntityMapper.selectAllSelective(userEntity);
    }

    /**
     * 获取空间文章
     *
     * @param username 用户名
     * @return 文章列表
     */
    public List<CompositionEntity> selectZoneCompositions(String username)
    {
        return compositionEntityMapper.selectByUsername(username);
    }

    /**
     * 获取指定文章
     *
     * @param compositionId 文章ID
     * @return 文章实体
     */
    public CompositionEntity selectCompositionById(Integer compositionId)
    {
        return compositionEntityMapper.selectByPrimaryKey(compositionId);
    }

    /**
     * 删除文章
     *
     * @param compositionId 文章ID
     * @return 是否删除成功
     */
    public boolean deleteComposition(Integer compositionId) throws BaseException
    {
        try{
            compositionEntityMapper.deleteByPrimaryKey(compositionId);
        } catch(DataIntegrityViolationException e) {
            logger.error("存在外键，无法删除");
        }
        return true;
    }

    /**
     * 更新文章
     *
     * @param compositionEntity 文章实体
     * @param compositionId 文章ID
     * @return 是否更新成功
     */
    public int updateComposition(CompositionEntity compositionEntity, Integer compositionId)
    {
        compositionEntity.setCompositionId(compositionId);
        return compositionEntityMapper.updateByPrimaryKeySelective(compositionEntity);
    }

    /**
     * 更新文章状态
     *
     * @param compositionEntity 文章实体
     * @param compositionId 文章ID
     * @return 是否更新成功
     */
    public int updateCompositionByValid(CompositionEntity compositionEntity, Integer compositionId)
    {
        compositionEntity.setCompositionId(compositionId);
        return compositionEntityMapper.updateByValid(compositionEntity);
    }

    /**
     * 获取文章统计
     *
     * @param compositionId 文章ID
     * @return 文章统计实体
     */
    public CompositionCountModel selectCountByCompositionId(Integer compositionId){
        return compositionEntityMapper.selectCountByCompositionId(compositionId);
    }
}
