package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.model.ZoneModel;
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
class ZoneServiceTest
{

    @Autowired
    private ZoneService zoneService;

    @Test
    void selectZone()
    {
        ZoneModel zoneModel = zoneService.selectZone("test");

        assertThat(zoneModel).isInstanceOf(ZoneModel.class);
    }
}