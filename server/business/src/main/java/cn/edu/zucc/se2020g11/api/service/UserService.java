package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.constant.ErrorDictionary;
import cn.edu.zucc.se2020g11.api.constant.LogCategory;
import cn.edu.zucc.se2020g11.api.dao.UserEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import cn.edu.zucc.se2020g11.api.model.LoginForm;
import cn.edu.zucc.se2020g11.api.model.SignupForm;
import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

/**
 * @author nonlinearthink
 */
@Service
public class UserService {

    private final Logger logger = LogManager.getLogger(LogCategory.BUSINESS);

    private final UserEntityMapper userEntityMapper;
    private final StringRedisTemplate stringRedisTemplate;

    @Autowired(required = false)
    public UserService(UserEntityMapper userEntityMapper, StringRedisTemplate stringRedisTemplate) {
        this.userEntityMapper = userEntityMapper;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private String oneWayEncryption(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    /**
     * 注册服务
     *
     * @param signupForm 注册表单
     * @throws BaseException 异常
     */
    @Transactional(rollbackFor = Exception.class)
    public void signup(SignupForm signupForm) throws BaseException {
        // 过滤已注册的用户
        if (userEntityMapper.selectByPrimaryKey(signupForm.getUsername()) != null) {
            throw new BaseException(ErrorDictionary.USERNAME_CONFLICTS, LogCategory.BUSINESS);
        }
        // DTO 转换成 Entity
        UserEntity user = signupForm.makeUserEntity();
        // 密码加密存储
        String passwordEncrypted = oneWayEncryption(user.getPassword());
        user.setPassword(passwordEncrypted);
        // 初始化用户昵称
        if (user.getNickname() == null) {
            user.setNickname(user.getUsername());
        }
        // 创建账号
        userEntityMapper.insert(user);
    }

    /**
     * 登录服务
     *
     * @param loginForm 登录表单
     * @throws BaseException 异常
     */
    @Transactional(rollbackFor = Exception.class)
    public void login(LoginForm loginForm) throws BaseException {
        // 查询用户
        UserEntity user = userEntityMapper.selectByPrimaryKey(loginForm.getUsername());
        if (user == null) {
            throw new BaseException(ErrorDictionary.USERNAME_NOT_EXIST, LogCategory.BUSINESS);
        } else if (!user.getPassword().equals(oneWayEncryption(loginForm.getPassword()))) {
            throw new BaseException(ErrorDictionary.INCORRECT_PASSWORD, LogCategory.BUSINESS);
        }
    }

}
