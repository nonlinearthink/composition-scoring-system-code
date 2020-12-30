package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.entity.FollowEntity;
import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import cn.edu.zucc.se2020g11.api.model.FollowModel;
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
class FollowServiceTest
{

    @Autowired
    private FollowService followService;

    @Test
    void addFollow()
    {
        FollowEntity followEntity = new FollowEntity();
        followEntity.setUsername("test");
        followEntity.setTargetUsername("wty");

        int num = followService.addFollow(followEntity);
        assertThat(num).isGreaterThan(0);
    }

    @Test
    void selectAllFollows()
    {
        FollowEntity followEntity = new FollowEntity();
        followEntity.setUsername("test");

        List<FollowEntity> followEntityList = followService.selectAllFollows(followEntity);
        assertThat(followEntityList).isNotEmpty()
                .hasOnlyElementsOfType(FollowEntity.class);
    }

    @Test
    void selectAllFollowers()
    {
        FollowEntity followEntity = new FollowEntity();
        followEntity.setTargetUsername("test");

        List<FollowEntity> followEntityList = followService.selectAllFollowers(followEntity);
        assertThat(followEntityList).isNotEmpty()
                .hasOnlyElementsOfType(FollowEntity.class);
    }

    @Test
    void findFollows()
    {
        List<FollowEntity> followEntityList = new ArrayList<>();
        FollowEntity followEntity = new FollowEntity();
        followEntity.setUsername("test");
        followEntity.setTargetUsername("nonlinearthink");
        followEntityList.add(followEntity);

        List<UserEntity> userEntityList = followService.findFollows(followEntityList);
        assertThat(userEntityList).isNotEmpty()
                .hasOnlyElementsOfType(UserEntity.class);
    }

    @Test
    void findFollowers()
    {
        List<FollowEntity> followEntityList = new ArrayList<>();
        FollowEntity followEntity = new FollowEntity();
        followEntity.setUsername("test");
        followEntity.setTargetUsername("nonlinearthink");
        followEntityList.add(followEntity);

        List<UserEntity> userEntityList = followService.findFollowers(followEntityList);
        assertThat(userEntityList).isNotEmpty()
                .hasOnlyElementsOfType(UserEntity.class);
    }

    @Test
    void findFollow()
    {
        Boolean isFollow = followService.findFollow("test", "nonlinearthink");

        assertThat(isFollow).isTrue();
    }

    @Test
    void getFollowInfo()
    {
        List<UserEntity> userEntityList = new ArrayList<>();
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("test");
        userEntityList.add(userEntity);

        List<FollowModel> followModelList = followService.getFollowInfo(userEntityList);
        assertThat(followModelList).isNotEmpty()
                .hasOnlyElementsOfType(FollowModel.class);
    }

    @Test
    void deleteFollow()
    {
        FollowEntity followEntity = new FollowEntity();
        followEntity.setUsername("test");
        followEntity.setTargetUsername("nonlinearthink");

        int num = followService.deleteFollow(followEntity);
        assertThat(num).isGreaterThan(0);
    }
}