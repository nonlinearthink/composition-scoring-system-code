package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.MessageEntity;
import org.springframework.stereotype.Component;

@Component
public interface MessageEntityMapper {
    int deleteByPrimaryKey(Integer messageId);

    int insert(MessageEntity record);

    int insertSelective(MessageEntity record);

    MessageEntity selectByPrimaryKey(Integer messageId);

    int updateByPrimaryKeySelective(MessageEntity record);

    int updateByPrimaryKey(MessageEntity record);
}