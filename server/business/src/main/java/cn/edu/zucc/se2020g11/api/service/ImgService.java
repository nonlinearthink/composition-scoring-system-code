package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.model.ImgModel;
import cn.edu.zucc.se2020g11.api.repository.ImgReposrtory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImgService
{
    @Autowired
    private ImgReposrtory reposrtory;

    public ImgModel add(ImgModel imgModel, String path) {
        imgModel.setUrl(path);
        return reposrtory.save(imgModel);
    }
}
