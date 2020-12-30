package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.entity.HelpEntity;
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
class HelpServiceTest
{

    @Autowired
    private HelpService helpService;

    @Test
    void addHelp()
    {
        HelpEntity helpEntity = new HelpEntity();
        helpEntity.setHelpTitle("test");
        helpEntity.setHelpBody("test");
        helpEntity.setAdminName("admin");

        int num = helpService.addHelp(helpEntity);
        assertThat(num).isGreaterThan(0);
    }

    @Test
    void deleteHelp()
    {
        int num = helpService.deleteHelp(1);
        assertThat(num).isGreaterThan(0);
    }

    @Test
    void selectAllHelps()
    {
        List<HelpEntity> helpEntityList = helpService.selectAllHelps();

        assertThat(helpEntityList).isNotEmpty()
                .hasOnlyElementsOfType(HelpEntity.class);
    }

    @Test
    void updateHelp()
    {
        HelpEntity helpEntity = new HelpEntity();
        helpEntity.setHelpTitle("test");
        helpEntity.setHelpBody("test");
        helpEntity.setAdminName("admin");

        int num = helpService.updateHelp(helpEntity, 1);
        assertThat(num).isGreaterThan(0);
    }
}