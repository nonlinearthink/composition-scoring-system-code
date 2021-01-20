package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.CommentEntity;
import cn.edu.zucc.se2020g11.api.model.CommentViewModel;
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
class CommentEntityMapperTest
{
    @Autowired
    private CommentEntityMapper commentEntityMapper;

    @Test
    void deleteByCompositionId()
    {
        assertThat(commentEntityMapper.deleteByCompositionId(336)).isGreaterThan(0);
    }

    @Test
    void updateByStatus()
    {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentId(80);
        commentEntity.setStatus(1);
        assertThat(commentEntityMapper.updateByStatus(commentEntity)).isGreaterThan(0);
    }

    @Test
    void selectAllSelective()
    {
        assertThat(commentEntityMapper.selectAllSelective(336)).isNotEmpty()
                .hasOnlyElementsOfType(CommentEntity.class);
    }

    @Test
    void selectAll()
    {
        assertThat(commentEntityMapper.selectAll()).isNotEmpty()
                .hasOnlyElementsOfType(CommentEntity.class);
    }

    @Test
    void countComment()
    {
        assertThat(commentEntityMapper.countComment(1)).isGreaterThan(0);
    }

    @Test
    void selectCommentView()
    {
        assertThat(commentEntityMapper.selectCommentView("unit")).isNotEmpty()
                .hasOnlyElementsOfType(CommentViewModel.class);
    }
}