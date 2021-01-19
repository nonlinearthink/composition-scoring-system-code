package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.dao.SystemMessageEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.SystemMessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Tuenity
 */
@Service
public class SystemMessageService
{
    private final SystemMessageEntityMapper systemMessageEntityMapper;

    @Autowired(required = false)
    public SystemMessageService(SystemMessageEntityMapper systemMessageEntityMapper)
    {
        this.systemMessageEntityMapper = systemMessageEntityMapper;
    }
    public int addSystemMessage(SystemMessageEntity systemMessageEntity)
    {
        systemMessageEntityMapper.insert(systemMessageEntity);
        return systemMessageEntity.getSystemMessageId();
    }
    public List<SystemMessageEntity> selectAllSystemMessages()
    {
        return systemMessageEntityMapper.selectAll();
    }
    public int deleteSystemMessage(Integer systemMessageId)
    {
        return systemMessageEntityMapper.deleteByPrimaryKey(systemMessageId);
    }
    public int updateSystemMessage(SystemMessageEntity systemMessageEntity, Integer systemMessageId)
    {
        systemMessageEntity.setSystemMessageId(systemMessageId);
        return systemMessageEntityMapper.updateByPrimaryKeySelective(systemMessageEntity);
    }
}
