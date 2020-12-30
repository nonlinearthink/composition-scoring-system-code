package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.entity.SystemMessageEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class SystemMessageServiceTest
{

    @Autowired
    private SystemMessageService systemMessageService;

    @Test
    void addSystemMessage()
    {
        SystemMessageEntity systemMessageEntity = new SystemMessageEntity();
        systemMessageEntity.setAdminName("admin");
        systemMessageEntity.setSMessageBody("test");

        int num = systemMessageService.addSystemMessage(systemMessageEntity);
        assertThat(num).isGreaterThan(0);
    }

    @Test
    void selectAllSystemMessages()
    {
        List<SystemMessageEntity> systemMessageEntityList = systemMessageService.selectAllSystemMessages();

        assertThat(systemMessageEntityList).isNotEmpty()
                .hasOnlyElementsOfType(SystemMessageEntity.class);
    }

    @Test
    void deleteSystemMessage()
    {
        int num = systemMessageService.deleteSystemMessage(6);

        assertThat(num).isGreaterThan(0);
    }

    @Test
    void updateSystemMessage()
    {
        SystemMessageEntity systemMessageEntity = new SystemMessageEntity();
        systemMessageEntity.setAdminName("admin");
        systemMessageEntity.setSMessageBody("test");

        int num = systemMessageService.updateSystemMessage(systemMessageEntity, 6);
        assertThat(num).isGreaterThan(0);
    }
}