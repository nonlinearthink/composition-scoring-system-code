package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.dao.*;
import cn.edu.zucc.se2020g11.api.entity.CompositionEntity;
import cn.edu.zucc.se2020g11.api.entity.FollowEntity;
import cn.edu.zucc.se2020g11.api.entity.PushArticleEntity;
import cn.edu.zucc.se2020g11.api.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HomeService
{
    private final PushArticleEntityMapper pushArticleEntityMapper;
    private final CompositionEntityMapper compositionEntityMapper;
    private final FollowEntityMapper followEntityMapper;
    private final SupportEntityMapper supportEntityMapper;
    private final CommentEntityMapper commentEntityMapper;
    private final UserEntityMapper userEntityMapper;
    private final HistoryEntityMapper historyEntityMapper;

    @Autowired(required = false)
    public HomeService(PushArticleEntityMapper pushArticleEntityMapper, CompositionEntityMapper compositionEntityMapper, FollowEntityMapper followEntityMapper, SupportEntityMapper supportEntityMapper, CommentEntityMapper commentEntityMapper, UserEntityMapper userEntityMapper, HistoryEntityMapper historyEntityMapper)
    {
        this.pushArticleEntityMapper = pushArticleEntityMapper;
        this.compositionEntityMapper = compositionEntityMapper;
        this.followEntityMapper = followEntityMapper;
        this.supportEntityMapper = supportEntityMapper;
        this.commentEntityMapper = commentEntityMapper;
        this.userEntityMapper = userEntityMapper;
        this.historyEntityMapper = historyEntityMapper;
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
                followCardModel.setCompositionId(c.getCompositionId());
                followCardModel.setAvatarUrl(userEntityMapper.selectByPrimaryKey(f.getTargetUsername()).getAvatarUrl());
                followCardModel.setNickname(userEntityMapper.selectByPrimaryKey(f.getTargetUsername()).getNickname());
                followCardModel.setTitle(c.getTitle());
                followCardModel.setCompositionBody(c.getCompositionBody());
                followCardModel.setReleaseTime(c.getReleaseTime());
                followCardModel.setSupportCount(supportEntityMapper.countSupport(c.getCompositionId()));
                followCardModel.setCommentCount(commentEntityMapper.countComment(c.getCompositionId()));
                followCardModel.setScore(c.getScore());
                followCardModelList.add(followCardModel);
            }
        }
        return followCardModelList;
    }
    public List<NewCardModel> selectNewCompositions()
    {
        List<CompositionEntity> compositionEntityList = compositionEntityMapper.selectNew();
        List<NewCardModel> newCardModelList = new ArrayList<>();
        for(CompositionEntity c : compositionEntityList){
            NewCardModel newCardModel = new NewCardModel();
            newCardModel.setCompositionId(c.getCompositionId());
            newCardModel.setAvatarUrl(userEntityMapper.selectByPrimaryKey(c.getUsername()).getAvatarUrl());
            newCardModel.setNickname(userEntityMapper.selectByPrimaryKey(c.getUsername()).getNickname());
            newCardModel.setTitle(c.getTitle());
            newCardModel.setCompositionBody(c.getCompositionBody());
            newCardModel.setReleaseTime(c.getReleaseTime());
            newCardModel.setHistoryCount(historyEntityMapper.countHistory(c.getCompositionId()));
            newCardModel.setCommentCount(commentEntityMapper.countComment(c.getCompositionId()));
            newCardModelList.add(newCardModel);
        }
        return newCardModelList;
    }
    public List<HotCardModel> selectHotCompositions()
    {
        List<CompositionCountModel> compositionCountModelList = compositionEntityMapper.selectHot();
        Map<Integer, Double> map = new HashMap();
        for(CompositionCountModel c : compositionCountModelList){
            //(总赞数*0.7+总收藏数*0.7+总评论数*0.7+总浏览数*0.3)*1000/(发布时间距离当前时间的小时差+2)^1.2
            map.put(c.getCompositionId(), (c.getSupportCount()*0.4 + c.getFavoriteCount()*0.3 + c.getCommentCount()*0.2 + c.getHistoryCount()*0.1) * 1000);
        }
        map = sortDescend(map);
        List<HotCardModel> hotCardModelList = new ArrayList<>();
        for (Map.Entry<Integer, Double> m : map.entrySet()) {
            HotCardModel hotCardModel = new HotCardModel();
            hotCardModel.setCompositionId(m.getKey());
            hotCardModel.setAvatarUrl(userEntityMapper.selectByPrimaryKey(compositionEntityMapper.selectByPrimaryKey(m.getKey()).getUsername()).getAvatarUrl());
            hotCardModel.setNickname(userEntityMapper.selectByPrimaryKey(compositionEntityMapper.selectByPrimaryKey(m.getKey()).getUsername()).getNickname());
            hotCardModel.setTitle(compositionEntityMapper.selectByPrimaryKey(m.getKey()).getTitle());
            hotCardModel.setCompositionBody(compositionEntityMapper.selectByPrimaryKey(m.getKey()).getCompositionBody());
            hotCardModel.setReleaseTime(compositionEntityMapper.selectByPrimaryKey(m.getKey()).getReleaseTime());
            hotCardModel.setHotCount(m.getValue());
            hotCardModelList.add(hotCardModel);
            if(hotCardModelList.size() == 10){
                break;
            }
        }
        return hotCardModelList;
    }

    public List<String> selectUserByUsername(String username){
        return userEntityMapper.selectUserByUsername("%" + username + "%");
    }

//    public List<UsernameCardModel> selectUserDetailByUsername(String username){
//        List<String> usernameList = userEntityMapper.selectUserByUsername("%" + username + "%");
//        List<UsernameCardModel> usernameCardModelList = new ArrayList<>();
//        for(String s : usernameList){
//            UsernameCardModel usernameCardModel = new UsernameCardModel();
//            usernameCardModel.setUsername(s);
//            usernameCardModel.setNickname(userEntityMapper.selectByPrimaryKey(s).getNickname());
//            usernameCardModel.setSignature(userEntityMapper.selectByPrimaryKey(s).getSignature());
//            usernameCardModel.setAvatarUrl(userEntityMapper.selectByPrimaryKey(s).getAvatarUrl());
//            usernameCardModel.setCompositionCount(666);
//            usernameCardModel.setFollowCount(666);
//            usernameCardModel.setIsFollow(true);
//            usernameCardModelList.add(usernameCardModel);
//        }
//
//        return usernameCardModelList;
//    }


    public List<UsernameCardModel> selectUserDetailByUsername(String username)
    {
        return userEntityMapper.selectUserView("%" + username + "%");
    }


    /**
     * Map的value值降序排序
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortDescend(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return -compare;
            }
        });

        Map<K, V> returnMap = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            returnMap.put(entry.getKey(), entry.getValue());
        }
        return returnMap;
    }

}
