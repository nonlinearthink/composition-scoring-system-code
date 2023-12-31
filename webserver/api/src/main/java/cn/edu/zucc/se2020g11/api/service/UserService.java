package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.constant.ErrorDictionary;
import cn.edu.zucc.se2020g11.api.constant.LogCategory;
import cn.edu.zucc.se2020g11.api.dao.UserEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import cn.edu.zucc.se2020g11.api.model.*;
import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;

/**
 * 用户服务层
 *
 * @author nonlinearthink
 */
@Service
public class UserService {

    private final Logger logger = LogManager.getLogger(LogCategory.BUSINESS.getPosition());

    private final UserEntityMapper userEntityMapper;

    @Autowired(required = false)
    public UserService(UserEntityMapper userEntityMapper) {
        this.userEntityMapper = userEntityMapper;
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
        // 过滤已注册的用户和邮箱
        if (userEntityMapper.selectByPrimaryKey(signupForm.getUsername()) != null) {
            throw new BaseException(ErrorDictionary.USERNAME_CONFLICTS, LogCategory.BUSINESS);
        } else if(userEntityMapper.selectByEmail(signupForm.getEmail()) != null) {
            throw new BaseException(ErrorDictionary.EMAIL_CONFLICTS, LogCategory.BUSINESS);
        }
        // DTO 转换成 Entity
        UserEntity user = signupForm.makeUserEntity();
        // 密码加密存储
        String passwordEncrypted = oneWayEncryption(user.getPassword());
        user.setPassword(passwordEncrypted);
        user.setFrozen(false);
        // 初始化用户昵称
        if (user.getNickname() == null) {
            user.setNickname(user.getUsername());
        }
        // 创建账号
        userEntityMapper.insert(user);
        logger.info("用户注册成功");
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

    /**
     * 换绑邮箱服务
     *
     * @param passwordForgetModel 忘记密码模型
     * @throws BaseException 异常
     */
    @Transactional(rollbackFor = Exception.class)
    public void forgetPassword(PasswordForgetModel passwordForgetModel) throws BaseException {
        // 验证用户绑定的邮箱
        if(userEntityMapper.selectByUsernameAndEmail(passwordForgetModel.getUsername(), passwordForgetModel.getEmail()) == null) {
            throw new BaseException(ErrorDictionary.EMAIL_ERROR, LogCategory.BUSINESS);
        }
        // DTO 转换成 Entity
        UserEntity user = passwordForgetModel.makeUserEntity();
        // 密码加密存储
        String passwordEncrypted = oneWayEncryption(user.getPassword());
        user.setPassword(passwordEncrypted);
        // 换绑邮箱
        userEntityMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 换绑邮箱服务
     *
     * @param emailChangeForm 邮箱换绑模型
     * @throws BaseException 异常
     */
    @Transactional(rollbackFor = Exception.class)
    public void changeEmail(EmailChangeForm emailChangeForm) throws BaseException {
        // 过滤已注册的邮箱
        if(userEntityMapper.selectByEmail(emailChangeForm.getNewEmail()) != null) {
            throw new BaseException(ErrorDictionary.EMAIL_CONFLICTS, LogCategory.BUSINESS);
        }
        // DTO 转换成 Entity
        UserEntity user = emailChangeForm.makeUserEntity();
        // 换绑邮箱
        userEntityMapper.updateByPrimaryKeySelective(user);
    }
}
