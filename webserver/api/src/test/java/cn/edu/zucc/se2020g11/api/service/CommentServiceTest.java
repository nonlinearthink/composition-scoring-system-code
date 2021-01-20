package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.entity.CommentEntity;
import cn.edu.zucc.se2020g11.api.model.CommentViewModel;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class CommentServiceTest
{

    @Autowired
    private CommentService commentService;

    @Test
    void selectAllComments()
    {
        List<CommentEntity> commentEntityList =  commentService.selectAllComments();

        assertThat(commentEntityList).isNotEmpty()
                .hasOnlyElementsOfType(CommentEntity.class);
    }

    @Test
    void selectComment()
    {
        List<CommentEntity> commentEntityList =  commentService.selectComment(1);

        assertThat(commentEntityList).isNotEmpty()
                .hasOnlyElementsOfType(CommentEntity.class);
    }

    @Test
    void selectCommentById()
    {
        CommentEntity commentEntity =  commentService.selectCommentById(1);

        assertThat(commentEntity).isInstanceOf(CommentEntity.class);
    }

    @Test
    void selectCommentView()
    {
        List<CommentViewModel> commentViewModelList =  commentService.selectCommentView("unit");

        assertThat(commentViewModelList).isNotEmpty()
                .hasOnlyElementsOfType(CommentViewModel.class);
    }

    @Test
    void addComment()
    {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setUsername("test");
        commentEntity.setCompositionId(1);
        commentEntity.setCommentBody("666");

        int num = commentService.addComment(commentEntity);
        assertThat(num).isGreaterThan(0);
    }

    @Test
    void deleteComment()
    {
        assertThatExceptionOfType(DataIntegrityViolationException.class).isThrownBy(() -> commentService.deleteComment(1));

        assertThat(commentService.deleteComment(80)).isTrue();
    }

    @Test
    void deleteCommentByCompositionId()
    {
        assertThatExceptionOfType(DataIntegrityViolationException.class).isThrownBy(() -> commentService.deleteCommentByCompositionId(1));

        assertThat(commentService.deleteCommentByCompositionId(336)).isTrue();
    }

    @Test
    void updateCommentByStatus()
    {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setStatus(0);

        int num = commentService.updateCommentByStatus(commentEntity, 1);
        assertThat(num).isGreaterThan(0);
    }
}