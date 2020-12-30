package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.entity.CompositionEntity;
import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import cn.edu.zucc.se2020g11.api.model.CompositionCountModel;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class CompositionServiceTest
{

    @Autowired
    private CompositionService compositionService;

    @Test
    void addComposition()
    {
        CompositionEntity compositionEntity = new CompositionEntity();
        compositionEntity.setUsername("test");
        compositionEntity.setCompositionBody("test");

        int num =  compositionService.addComposition(compositionEntity);
        assertThat(num).isGreaterThan(0);
    }

    @Test
    void selectAllCompositions()
    {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("test");

        List<CompositionEntity> compositionEntityList =  compositionService.selectAllCompositions(userEntity);
        assertThat(compositionEntityList).isNotEmpty()
                .hasOnlyElementsOfType(CompositionEntity.class);
    }

    @Test
    void selectZoneCompositions()
    {
        List<CompositionEntity>  compositionEntityList = compositionService.selectZoneCompositions("test");

        assertThat(compositionEntityList).isNotEmpty()
                .hasOnlyElementsOfType(CompositionEntity.class);
    }

    @Test
    void selectCompositionById()
    {
        CompositionEntity compositionEntity =  compositionService.selectCompositionById(1);

        assertThat(compositionEntity).isInstanceOf(CompositionEntity.class);
    }

    @Test
    void deleteComposition()
    {
        assertThatExceptionOfType(DataIntegrityViolationException.class).isThrownBy(() -> compositionService.deleteComposition(1));
    }

    @Test
    void updateComposition()
    {
        CompositionEntity compositionEntity = new CompositionEntity();
        compositionEntity.setUsername("test");
        compositionEntity.setCompositionBody("test");

        int num = compositionService.updateComposition(compositionEntity, 1);
        assertThat(num).isGreaterThan(0);
    }

    @Test
    void updateCompositionByValid()
    {
        CompositionEntity compositionEntity = new CompositionEntity();
        compositionEntity.setValid(1);

        int num = compositionService.updateCompositionByValid(compositionEntity, 1);
        assertThat(num).isGreaterThan(0);
    }

    @Test
    void selectCountByCompositionId()
    {
        CompositionCountModel  compositionCountModel = compositionService.selectCountByCompositionId(1);

        assertThat(compositionCountModel).isInstanceOf(CompositionCountModel.class);
    }
}