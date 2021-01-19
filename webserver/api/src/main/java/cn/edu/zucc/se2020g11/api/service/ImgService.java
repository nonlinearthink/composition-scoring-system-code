package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.model.ImgModel;
import cn.edu.zucc.se2020g11.api.repository.ImgRepository;
import org.springframework.stereotype.Service;

/**
 * 图片服务层
 *
 * @author Tuenity
 */
@Service
public class ImgService
{
    private final ImgRepository repository;

    public ImgService(ImgRepository repository)
    {
        this.repository = repository;
    }

    /**
     * 添加图片
     *
     * @param imgModel 图片模型
     * @param path 路径
     * @return 图片模型
     */
    public ImgModel add(ImgModel imgModel, String path) {
        imgModel.setUrl(path);
        return repository.save(imgModel);
    }
}
