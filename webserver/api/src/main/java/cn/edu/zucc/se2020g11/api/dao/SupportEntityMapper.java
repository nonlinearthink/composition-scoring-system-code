package cn.edu.zucc.se2020g11.api.dao;

import cn.edu.zucc.se2020g11.api.entity.SupportEntity;
import cn.edu.zucc.se2020g11.api.model.SupportViewModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SupportEntityMapper {
    int deleteByPrimaryKey(Integer supportId);

    int deleteByUsernameAndCompositionId(SupportEntity record);

    int deleteByCompositionId(Integer compositionId);

    int insert(SupportEntity record);

    int insertSelective(SupportEntity record);

    SupportEntity selectByPrimaryKey(Integer supportId);

    int updateByPrimaryKeySelective(SupportEntity record);

    int updateByPrimaryKey(SupportEntity record);

    List<SupportEntity> selectAllSelective(String username);

    List<SupportEntity> selectByUsernameAndCompositionId(SupportEntity record);

    Integer countSupport(Integer compositionId);

    List<SupportViewModel> selectSupportView(String targetUsername);
}