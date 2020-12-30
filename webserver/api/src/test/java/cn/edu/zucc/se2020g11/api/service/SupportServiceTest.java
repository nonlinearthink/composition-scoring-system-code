package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.entity.CompositionEntity;
import cn.edu.zucc.se2020g11.api.entity.FollowEntity;
import cn.edu.zucc.se2020g11.api.entity.SupportEntity;
import cn.edu.zucc.se2020g11.api.model.SupportViewModel;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class SupportServiceTest
{

    @Autowired
    private SupportService supportService;

    @Test
    void addSupport()
    {
        SupportEntity supportEntity = new SupportEntity();
        supportEntity.setUsername("test");
        supportEntity.setCompositionId(3);

        int num = supportService.addSupport(supportEntity);
        assertThat(num).isGreaterThan(0);
    }

    @Test
    void selectAllSupports()
    {
        List<SupportEntity> supportEntityList = supportService.selectAllSupports("test");

        assertThat(supportEntityList).isNotEmpty()
                .hasOnlyElementsOfType(SupportEntity.class);
    }

    @Test
    void selectSupportView()
    {
        List<SupportViewModel> supportViewModelList = supportService.selectSupportView("test");

        assertThat(supportViewModelList).isNotEmpty()
                .hasOnlyElementsOfType(SupportViewModel.class);
    }

    @Test
    void findSupportedComposition()
    {
        List<SupportEntity> supportEntityList = new ArrayList<>();
        SupportEntity supportEntity = new SupportEntity();
        supportEntity.setUsername("test");
        supportEntity.setCompositionId(1);
        supportEntityList.add(supportEntity);

        List<CompositionEntity> compositionEntityList = supportService.findSupportedComposition(supportEntityList);
        assertThat(compositionEntityList).isNotEmpty()
                .hasOnlyElementsOfType(CompositionEntity.class);
    }

    @Test
    void findSupport()
    {
        Boolean isSupport = supportService.findSupport("test", 1);

        assertThat(isSupport).isTrue();
    }

    @Test
    void deleteSupport()
    {
        SupportEntity supportEntity = new SupportEntity();
        supportEntity.setUsername("test");
        supportEntity.setCompositionId(1);

        int num = supportService.deleteSupport(supportEntity);
        assertThat(num).isGreaterThan(0);
    }

    @Test
    void deleteSupportByCompositionId()
    {
        int num = supportService.deleteSupportByCompositionId(1);

        assertThat(num).isGreaterThan(0);
    }
}