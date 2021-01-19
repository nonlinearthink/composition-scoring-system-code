package cn.edu.zucc.se2020g11.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 图片模型
 *
 * @author nonlinearthink
 */
@Entity(name = "img")
@Setter
@Getter
public class ImgModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String url;
}
