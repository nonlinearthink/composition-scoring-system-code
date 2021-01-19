package cn.edu.zucc.se2020g11.api.repository;

import cn.edu.zucc.se2020g11.api.model.ImgModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 图片工厂
 *
 * @author nonlinearthink
 */
public interface ImgRepository extends JpaRepository<ImgModel,Integer>
{
}
