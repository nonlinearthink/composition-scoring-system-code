package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.constant.ErrorDictionary;
import cn.edu.zucc.se2020g11.api.constant.LogCategory;
import cn.edu.zucc.se2020g11.api.dao.UserEntityMapper;
import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import cn.edu.zucc.se2020g11.api.model.SignupForm;
import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserService {

    private final Logger logger = LogManager.getLogger(LogCategory.BUSINESS);

    private final UserEntityMapper userEntityMapper;

    @Autowired(required = false)
    public UserService(UserEntityMapper userEntityMapper) {
        this.userEntityMapper = userEntityMapper;
    }

    public void signup(SignupForm signupForm) throws BaseException {
        // 过滤非法操作
        if (signupForm.getUsername() == null) {
            throw new BaseException(ErrorDictionary.USERNAME_IS_NULL, LogCategory.BUSINESS);
        } else if (signupForm.getPassword() == null) {
            throw new BaseException(ErrorDictionary.PASSWORD_IS_NULL, LogCategory.BUSINESS);
        } else if (signupForm.getEmail() == null) {
            throw new BaseException(ErrorDictionary.EMAIL_IS_NULL, LogCategory.BUSINESS);
        }
        // 过滤已注册的用户
        if (userEntityMapper.selectByPrimaryKey(signupForm.getUsername()) != null) {
            throw new BaseException(ErrorDictionary.USERNAME_CONFLICTS, LogCategory.BUSINESS);
        }
        UserEntity user = signupForm.makeUserEntity();
        // 密码加密存储
        String passwordEncrypted = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(passwordEncrypted);
        // 初始化用户昵称
        if (user.getNickname() == null) {
            user.setNickname(user.getUsername());
        }
        // 创建账号
        userEntityMapper.insert(user);
    }

}
