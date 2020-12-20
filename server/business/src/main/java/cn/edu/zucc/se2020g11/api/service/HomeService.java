package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.dao.CompositionEntityMapper;
import cn.edu.zucc.se2020g11.api.dao.FollowEntityMapper;
import cn.edu.zucc.se2020g11.api.dao.PushArticleEntityMapper;
import cn.edu.zucc.se2020g11.api.dao.SupportEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.CompositionEntity;
import cn.edu.zucc.se2020g11.api.entity.FollowEntity;
import cn.edu.zucc.se2020g11.api.entity.PushArticleEntity;
import cn.edu.zucc.se2020g11.api.model.ArticleModel;
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

    @Autowired(required = false)
    public HomeService(PushArticleEntityMapper pushArticleEntityMapper, CompositionEntityMapper compositionEntityMapper, FollowEntityMapper followEntityMapper, SupportEntityMapper supportEntityMapper)
    {
        this.pushArticleEntityMapper = pushArticleEntityMapper;
        this.compositionEntityMapper = compositionEntityMapper;
        this.followEntityMapper = followEntityMapper;
        this.supportEntityMapper = supportEntityMapper;
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
    public List<CompositionEntity> selectFollowCompositions(String username)
    {
        FollowEntity followEntity = new FollowEntity();
        followEntity.setUsername(username);
        List<FollowEntity> followEntityList = followEntityMapper.selectAllSelective(followEntity);
        List<CompositionEntity> followCompositionList = new ArrayList<>();
        for(FollowEntity f : followEntityList){
            List<CompositionEntity> compositionEntityList = compositionEntityMapper.selectFollow(f.getTargetUsername());
            for(CompositionEntity c : compositionEntityList){
                followCompositionList.add(c);
            }
        }
        return followCompositionList;
    }
    public int countSupport(Integer compositionId)
    {
        return supportEntityMapper.countSupport(compositionId);
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
