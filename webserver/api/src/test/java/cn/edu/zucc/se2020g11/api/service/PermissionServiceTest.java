package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class PermissionServiceTest
{

    @Autowired
    private PermissionService permissionService;

    @Test
    void validateComposition()
    {
        assertThatExceptionOfType(BaseException.class).isThrownBy(() -> permissionService.validateComposition("unit", 289));
        assertThat(permissionService.validateComposition("unit", 336)).isTrue();
    }

    @Test
    void validateComment()
    {
        assertThatExceptionOfType(BaseException.class).isThrownBy(() -> permissionService.validateComment("unit", 77));
        assertThat(permissionService.validateComment("unit", 80)).isTrue();
    }
}