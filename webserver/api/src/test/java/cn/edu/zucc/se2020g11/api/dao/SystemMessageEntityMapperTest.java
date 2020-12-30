package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.SystemMessageEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class SystemMessageEntityMapperTest
{
    @Autowired
    private SystemMessageEntityMapper systemMessageEntityMapper;

    @Test
    void selectAll()
    {
        assertThat(systemMessageEntityMapper.selectAll()).isNotEmpty()
                .hasOnlyElementsOfType(SystemMessageEntity.class);
    }
}