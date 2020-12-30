package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.CommentReportEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class CommentEntityMapperTest
{
    @Autowired
    private CommentEntityMapper commentEntityMapper;

    @Test
    void deleteByCompositionId()
    {
        assertThat(commentEntityMapper.deleteByCompositionId(3)).isGreaterThan(0);
    }

    @Test
    void updateByStatus()
    {
    }

    @Test
    void selectAllSelective()
    {
    }

    @Test
    void selectAll()
    {
    }

    @Test
    void countComment()
    {
    }

    @Test
    void selectCommentView()
    {
    }
}