package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.constant.ErrorDictionary;
import cn.edu.zucc.se2020g11.api.constant.LogCategory;
import cn.edu.zucc.se2020g11.api.dao.UserEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import cn.edu.zucc.se2020g11.api.model.LoginForm;
import cn.edu.zucc.se2020g11.api.model.PasswordChangeModel;
import cn.edu.zucc.se2020g11.api.model.SignupForm;
import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import org.apache.catalina.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author nonlinearthink
 */
@Service
public class UserService {

    private final Logger logger = LogManager.getLogger(LogCategory.BUSINESS.getPosition());

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
    public UserEntity login(LoginForm loginForm) throws BaseException {
        // 查询用户
        UserEntity user = userEntityMapper.selectByPrimaryKey(loginForm.getUsername());
        if (user == null) {
            throw new BaseException(ErrorDictionary.USERNAME_NOT_EXIST, LogCategory.BUSINESS);
        } else if (!user.getPassword().equals(oneWayEncryption(loginForm.getPassword()))) {
            throw new BaseException(ErrorDictionary.INCORRECT_PASSWORD, LogCategory.BUSINESS);
        }
        if(user.getFrozen()){
            if(new Date().before(user.getDefrostingTime())){
                throw new BaseException(ErrorDictionary.FROZEN_USER, LogCategory.BUSINESS);
            } else {
                user.setFrozen(false);
                userEntityMapper.updateUser(user);
            }
        }
        return user;
    }

    /**
     * 更改密码服务
     *
     * @param passwordChangeModel 密码修改模型
     * @throws BaseException 异常
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateUserPassword(String username, PasswordChangeModel passwordChangeModel) throws BaseException {
        // 查询用户
        UserEntity user = userEntityMapper.selectByPrimaryKey(username);
        if (user == null) {
            throw new BaseException(ErrorDictionary.USERNAME_NOT_EXIST, LogCategory.BUSINESS);
        } else if (!user.getPassword().equals(oneWayEncryption(passwordChangeModel.getOldPassword()))) {
            throw new BaseException(ErrorDictionary.INCORRECT_PASSWORD, LogCategory.BUSINESS);
        }
        // 修改密码
        user.setPassword(oneWayEncryption(passwordChangeModel.getNewPassword()));
        userEntityMapper.updateByPrimaryKey(user);
    }

    /**
     * 更改密码服务
     *
     * @param userEntity 用户信息修改模型
     * @throws BaseException 异常
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateUserDetail(String username, UserEntity userEntity) throws BaseException {
        // 修改信息
        userEntity.setUsername(username);
        userEntityMapper.updateByPrimaryKeySelective(userEntity);
    }

    /**
     * 更改密码服务
     *
     * @param userEntity 用户信息修改模型
     * @throws BaseException 异常
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(String username, UserEntity userEntity) throws BaseException {
        // 修改信息
        userEntity.setUsername(username);
        userEntityMapper.updateUser(userEntity);
    }

}
