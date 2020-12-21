package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.dao.*;
import cn.edu.zucc.se2020g11.api.entity.CompositionEntity;
import cn.edu.zucc.se2020g11.api.entity.FollowEntity;
import cn.edu.zucc.se2020g11.api.entity.PushArticleEntity;
import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import cn.edu.zucc.se2020g11.api.model.ArticleModel;
import cn.edu.zucc.se2020g11.api.model.FollowCardModel;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeService
{
    private final PushArticleEntityMapper pushArticleEntityMapper;
    private final CompositionEntityMapper compositionEntityMapper;
    private final FollowEntityMapper followEntityMapper;
    private final SupportEntityMapper supportEntityMapper;
    private final CommentEntityMapper commentEntityMapper;
    private final UserEntityMapper userEntityMapper;

    @Autowired(required = false)
    public HomeService(PushArticleEntityMapper pushArticleEntityMapper, CompositionEntityMapper compositionEntityMapper, FollowEntityMapper followEntityMapper, SupportEntityMapper supportEntityMapper, CommentEntityMapper commentEntityMapper, UserEntityMapper userEntityMapper)
    {
        this.pushArticleEntityMapper = pushArticleEntityMapper;
        this.compositionEntityMapper = compositionEntityMapper;
        this.followEntityMapper = followEntityMapper;
        this.supportEntityMapper = supportEntityMapper;
        this.commentEntityMapper = commentEntityMapper;
        this.userEntityMapper = userEntityMapper;
    }
    public List<ArticleModel> selectAllArticles()
    {
        List<PushArticleEntity> pushArticleEntityList = pushArticleEntityMapper.selectAll();
        List<ArticleModel> articleModelList = new ArrayList<>();
        for(PushArticleEntity p : pushArticleEntityList){
            ArticleModel articleModel = new ArticleModel();
            articleModel.setArticleId(p.getArticleId());
            articleModel.setArticleTitle(p.getArticleTitle());
            articleModel.setArticleBody(p.getArticleBody());
            articleModel.setTime(p.getTime());
            articleModelList.add(articleModel);
        }
        return articleModelList;
    }
    public List<FollowCardModel> selectFollowCompositions(String username)
    {
        FollowEntity followEntity = new FollowEntity();
        followEntity.setUsername(username);
        List<FollowEntity> followEntityList = followEntityMapper.selectAllSelective(followEntity);
        List<FollowCardModel> followCardModelList = new ArrayList<>();
        for(FollowEntity f : followEntityList){
            List<CompositionEntity> compositionEntityList = compositionEntityMapper.selectFollow(f.getTargetUsername());
            for(CompositionEntity c : compositionEntityList){
                FollowCardModel followCardModel = new FollowCardModel();
                followCardModel.setAvatarUrl(userEntityMapper.selectByPrimaryKey(f.getTargetUsername()).getAvatarUrl());
                followCardModel.setNickname(userEntityMapper.selectByPrimaryKey(f.getTargetUsername()).getNickname());
                followCardModel.setTitle(c.getTitle());
                followCardModel.setCompositionBody(c.getCompositionBody());
                followCardModel.setReleaseTime(c.getReleaseTime());
                followCardModel.setSupportCount(supportEntityMapper.countSupport(c.getCompositionId()));
                followCardModel.setCommentCount(commentEntityMapper.countComment(c.getCompositionId()));
                followCardModelList.add(followCardModel);
            }
        }
        return followCardModelList;
    }
//    public List<CompositionEntity> selectNewCompositions()
//    {
//        followEntity.setUsername(username);
//        List<FollowEntity> followEntityList = followEntityMapper.selectAllSelective(followEntity);
//        List<CompositionEntity> followCompositionList = new ArrayList<>();
//        for(FollowEntity f : followEntityList){
//            List<CompositionEntity> compositionEntityList = compositionEntityMapper.selectFollow(f.getTargetUsername());
//            for(CompositionEntity c : compositionEntityList){
//                followCompositionList.add(c);
//            }
//        }
//        return followCompositionList;
//    }

}
