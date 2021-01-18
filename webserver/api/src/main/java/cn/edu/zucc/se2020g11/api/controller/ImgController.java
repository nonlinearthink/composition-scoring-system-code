package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.constant.LogCategory;
import cn.edu.zucc.se2020g11.api.model.ApiResult;
import cn.edu.zucc.se2020g11.api.model.ImgModel;
import cn.edu.zucc.se2020g11.api.service.ImgService;
import io.swagger.annotations.Api;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tuenity
 */
@RestController
@RequestMapping("/api/img")
@Api(value = "ImgController")
public class ImgController
{
    /**
     * 获取本机ip
     */
    @Value("${res.serverIp}")
    private String host;
    /**
     * 获取主机端口
     */
    @Value("${server.port}")
    private String port;
    /**
     * 图片存放根路径
     */
    @Value("${res.storePath}")
    private String rootPath;
    /**
     * 图片存放根目录下的子目录
     */
    @Value("${res.imagePath}")
    private String sonPath;
    /**
     * 获取图片链接
     */
    private String imgPath;
    private final ImgService imgService;
    private final Logger logger = LogManager.getLogger(LogCategory.SYSTEM);

    @Autowired
    public ImgController(ImgService imgService) {
        this.imgService = imgService;
    }

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<ApiResult<Map<String, Object>>> upload(@RequestParam("img") MultipartFile file) {

        ApiResult<Map<String, Object>> result = new ApiResult<>();
        Map<String, Object> data = new HashMap<>(1);

        //返回上传的文件是否为空，即没有选择任何文件，或者所选文件没有内容。
        //防止上传空文件导致奔溃
        if (file.isEmpty()) {
            result.setMsg("文件为空");
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }

        // 获取文件名
        String fileName = file.getOriginalFilename();
        //logger.info("上传的文件名为：" + fileName);
        // 设置文件上传后的路径
        String filePath = rootPath + sonPath;
        logger.info("上传的文件路径" + filePath);
        logger.info("整个图片路径：" + host + ":" + port + sonPath + fileName);
        //创建文件路径
        File dest = new File(filePath + fileName);

        String imgPath = (host + ":" + port + sonPath + fileName).toString();

        // 解决中文问题，linux下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;

        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            //假如文件不存在即重新创建新的文件已防止异常发生
            dest.getParentFile().mkdirs();
        }

        try {
            //transferTo（dest）方法将上传文件写到服务器上指定的文件
            file.transferTo(dest);
            //将链接保存到URL中
            ImgModel imgModel = imgService.add(new ImgModel(), imgPath);
            result.setMsg("上传成功");
            data.put("imgModel", imgModel);
            result.setData(data);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            result.setMsg("上传失败");
            result.setData(data);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

}
