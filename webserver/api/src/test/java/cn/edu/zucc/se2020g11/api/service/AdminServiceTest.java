package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.entity.AdminEntity;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AdminServiceTest
{
    @Autowired
    private AdminService adminService;

    @Test
    public void countData(){
        Map<String, Object> dataCount = adminService.countData();
        Assert.assertEquals(dataCount.size(), 2);
        assertThat(dataCount)
                .hasSize(2)
                .extractingByKey("userCount", as(InstanceOfAssertFactories.INTEGER));
    }
}