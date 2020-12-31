package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.entity.ErrorEntity;
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
class ErrorServiceTest
{

    @Autowired
    private ErrorService errorService;

    @Test
    void addError()
    {
        ErrorEntity errorEntity = new ErrorEntity();
        errorEntity.setCompositionId(1);
        errorEntity.setText("test");

        int num =  errorService.addError(errorEntity);
        assertThat(num).isGreaterThan(0);
    }

    @Test
    void selectError()
    {
        ErrorEntity errorEntity = errorService.selectError(1, "0");

        assertThat(errorEntity).isInstanceOf(ErrorEntity.class);
    }

    @Test
    void deleteErrorByCompositionId()
    {
        int num = errorService.deleteErrorByCompositionId(1);

        assertThat(num).isGreaterThan(0);
    }
}