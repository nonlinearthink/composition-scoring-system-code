package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.dao.SystemMessageEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.SystemMessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统消息服务层
 *
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

    /**
     * 添加系统消息
     *
     * @param systemMessageEntity 系统消息实体
     * @return 系统消息ID
     */
    public int addSystemMessage(SystemMessageEntity systemMessageEntity)
    {
        systemMessageEntityMapper.insert(systemMessageEntity);
        return systemMessageEntity.getSystemMessageId();
    }

    /**
     * 获取系统消息
     *
     * @return 系统消息列表
     */
    public List<SystemMessageEntity> selectAllSystemMessages()
    {
        return systemMessageEntityMapper.selectAll();
    }

    /**
     * 删除系统消息
     *
     * @param systemMessageId 系统消息ID
     * @return 是否删除成功
     */
    public int deleteSystemMessage(Integer systemMessageId)
    {
        return systemMessageEntityMapper.deleteByPrimaryKey(systemMessageId);
    }

    /**
     * 更新系统消息
     *
     * @param systemMessageEntity 系统消息实体
     * @param systemMessageId 系统消息ID
     * @return 是否更新成功
     */
    public int updateSystemMessage(SystemMessageEntity systemMessageEntity, Integer systemMessageId)
    {
        systemMessageEntity.setSystemMessageId(systemMessageId);
        return systemMessageEntityMapper.updateByPrimaryKeySelective(systemMessageEntity);
    }
}
