package cn.edu.zucc.se2020g11.api.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
class AdminServiceTest
{
    @Autowired
    private AdminService adminService;

    @Test
    public void countData(){
        Map<String, Object> dataCount = adminService.countData();
        assertThat(dataCount).containsOnlyKeys("userCount", "compositionCount");
    }
}