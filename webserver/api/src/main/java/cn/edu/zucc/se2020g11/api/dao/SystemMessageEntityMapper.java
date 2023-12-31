package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.SystemMessageEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SystemMessageEntityMapper {
    int deleteByPrimaryKey(Integer systemMessageId);

    int insert(SystemMessageEntity record);

    int insertSelective(SystemMessageEntity record);

    SystemMessageEntity selectByPrimaryKey(Integer systemMessageId);

    List<SystemMessageEntity> selectAll();

    int updateByPrimaryKeySelective(SystemMessageEntity record);

    int updateByPrimaryKey(SystemMessageEntity record);
}