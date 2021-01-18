package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.dao.HelpEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.HelpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
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
    public int addHelp(HelpEntity helpEntity)
    {
        helpEntityMapper.insert(helpEntity);
        return helpEntity.getHelpId();
    }
    public int deleteHelp(Integer helpId)
    {
        return helpEntityMapper.deleteByPrimaryKey(helpId);
    }
    public List<HelpEntity> selectAllHelps()
    {
        return helpEntityMapper.selectAll();
    }
    public int updateHelp(HelpEntity helpEntity, Integer helpId)
    {
        helpEntity.setHelpId(helpId);
        return helpEntityMapper.updateByPrimaryKeySelective(helpEntity);
    }
}
