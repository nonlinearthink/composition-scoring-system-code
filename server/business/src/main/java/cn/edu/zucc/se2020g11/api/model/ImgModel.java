package cn.edu.zucc.se2020g11.api.entity;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * 密码修改模型
 *
 * @author nonlinearthink
 */
@Entity(name = "img")
public class ImgEntity
{
    private Integer id;

    private String url;
}
