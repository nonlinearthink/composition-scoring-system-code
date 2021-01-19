package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.constant.ErrorDictionary;
import cn.edu.zucc.se2020g11.api.constant.LogCategory;
import cn.edu.zucc.se2020g11.api.dao.AdminEntityMapper;
import cn.edu.zucc.se2020g11.api.dao.CompositionEntityMapper;
import cn.edu.zucc.se2020g11.api.dao.HistoryEntityMapper;
import cn.edu.zucc.se2020g11.api.dao.UserEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.AdminEntity;
import cn.edu.zucc.se2020g11.api.model.AdminLoginForm;
import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nonlinearthink
 */
@Service
public class AdminService
{
    private final Logger logger = LogManager.getLogger(LogCategory.BUSINESS.getPosition());

    private final AdminEntityMapper adminEntityMapper;
    private final CompositionEntityMapper compositionEntityMapper;
    private final UserEntityMapper userEntityMapper;
    private final HistoryEntityMapper historyEntityMapper;

    @Autowired(required = false)
    public AdminService(AdminEntityMapper adminEntityMapper, CompositionEntityMapper compositionEntityMapper, UserEntityMapper userEntityMapper, HistoryEntityMapper historyEntityMapper) {
        this.adminEntityMapper = adminEntityMapper;
        this.compositionEntityMapper = compositionEntityMapper;
        this.userEntityMapper = userEntityMapper;
        this.historyEntityMapper = historyEntityMapper;
    }

    /**
     * 登录服务
     *
     * @param adminLoginForm 登录表单
     * @throws BaseException 异常
     */
    @Transactional(rollbackFor = Exception.class)
    public AdminEntity login(AdminLoginForm adminLoginForm) throws BaseException {
        // 查询管理员
        AdminEntity adminEntity = adminEntityMapper.selectByPrimaryKey(adminLoginForm.getAdminName());
        if (adminEntity == null) {
            throw new BaseException(ErrorDictionary.USERNAME_NOT_EXIST, LogCategory.BUSINESS);
        } else if (!adminEntity.getPassword().equals(adminLoginForm.getPassword())) {
            throw new BaseException(ErrorDictionary.INCORRECT_PASSWORD, LogCategory.BUSINESS);
        }
        return adminEntity;
    }

    /**
     * 统计数据
     *
     * @throws BaseException 异常
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> countData() throws BaseException {

        Map<String, Object> data = new HashMap<>(1);
        data.put("userCount", userEntityMapper.selectSum());
        data.put("compositionCount", compositionEntityMapper.selectSum());
        data.put("userTodayCount", userEntityMapper.selectSumByToday());
        data.put("compositionTodayCount", compositionEntityMapper.selectSumByToday());
        data.put("historyCount", historyEntityMapper.selectHistory());

        return data;
    }
}
