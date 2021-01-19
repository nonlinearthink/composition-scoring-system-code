package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.dao.HelpEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.HelpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 帮助服务层
 *
 * @author Tuenity
 */
@Service
public class HelpService
{
    private final HelpEntityMapper helpEntityMapper;

    @Autowired(required = false)
    public HelpService(HelpEntityMapper helpEntityMapper)
    {
        this.helpEntityMapper = helpEntityMapper;
    }

    /**
     * 添加帮助
     *
     * @param helpEntity 帮助实体
     * @return 帮助ID
     */
    public int addHelp(HelpEntity helpEntity)
    {
        helpEntityMapper.insert(helpEntity);
        return helpEntity.getHelpId();
    }

    /**
     * 删除帮助
     *
     * @param helpId 帮助ID
     * @return 是否删除成功
     */
    public int deleteHelp(Integer helpId)
    {
        return helpEntityMapper.deleteByPrimaryKey(helpId);
    }

    /**
     * 获取所有帮助
     *
     * @return 帮助列表
     */
    public List<HelpEntity> selectAllHelps()
    {
        return helpEntityMapper.selectAll();
    }

    /**
     * 更新帮助
     *
     * @param helpEntity 帮助实体
     * @param helpId 帮助ID
     * @return 是否更新成功
     */
    public int updateHelp(HelpEntity helpEntity, Integer helpId)
    {
        helpEntity.setHelpId(helpId);
        return helpEntityMapper.updateByPrimaryKeySelective(helpEntity);
    }
}
