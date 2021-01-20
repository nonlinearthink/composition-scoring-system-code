package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.entity.CompositionEntity;
import cn.edu.zucc.se2020g11.api.entity.FavoriteEntity;
import cn.edu.zucc.se2020g11.api.entity.FollowEntity;
import cn.edu.zucc.se2020g11.api.entity.SupportEntity;
import cn.edu.zucc.se2020g11.api.model.SupportViewModel;
import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

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
        // 正常点赞
        SupportEntity supportEntity = new SupportEntity();
        supportEntity.setUsername("unit");
        supportEntity.setCompositionId(338);

        int num = supportService.addSupport(supportEntity);
        assertThat(num).isGreaterThan(0);

        // 重复点赞
        supportEntity = new SupportEntity();
        supportEntity.setUsername("unit");
        supportEntity.setCompositionId(336);

        SupportEntity finalSupportEntity = supportEntity;
        assertThatExceptionOfType(BaseException.class).isThrownBy(() -> supportService.addSupport(finalSupportEntity));
    }

    @Test
    void selectAllSupports()
    {
        List<SupportEntity> supportEntityList = supportService.selectAllSupports("unit");

        assertThat(supportEntityList).isNotEmpty()
                .hasOnlyElementsOfType(SupportEntity.class);
    }

    @Test
    void selectSupportView()
    {
        List<SupportViewModel> supportViewModelList = supportService.selectSupportView("unit");

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
        Boolean isSupport = supportService.findSupport("unit", 336);

        assertThat(isSupport).isTrue();
    }

    @Test
    void deleteSupport()
    {
        // 正常取消点赞
        SupportEntity supportEntity = new SupportEntity();
        supportEntity.setUsername("unit");
        supportEntity.setCompositionId(336);

        int num = supportService.deleteSupport(supportEntity);
        assertThat(num).isGreaterThan(0);

        // 未点赞取消点赞
        supportEntity = new SupportEntity();
        supportEntity.setUsername("unit");
        supportEntity.setCompositionId(336);

        SupportEntity finalSupportEntity = supportEntity;
        assertThatExceptionOfType(BaseException.class).isThrownBy(() -> supportService.deleteSupport(finalSupportEntity));
    }

    @Test
    void deleteSupportByCompositionId()
    {
        int num = supportService.deleteSupportByCompositionId(336);

        assertThat(num).isGreaterThan(0);
    }
}