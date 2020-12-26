package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.dao.HelpEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.HelpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void deleteHelp(Integer helpId)
    {
        helpEntityMapper.deleteByPrimaryKey(helpId);
    }
    public List<HelpEntity> selectAllHelps()
    {
        return helpEntityMapper.selectAll();
    }
    public void updateHelp(HelpEntity helpEntity, Integer helpId)
    {
        helpEntity.setHelpId(helpId);
        helpEntityMapper.updateByPrimaryKeySelective(helpEntity);
    }
}
