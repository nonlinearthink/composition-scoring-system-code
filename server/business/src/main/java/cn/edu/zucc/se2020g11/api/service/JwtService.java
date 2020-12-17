package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.constant.UserType;
import cn.edu.zucc.se2020g11.api.constant.LogCategory;
import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import cn.edu.zucc.se2020g11.api.constant.ErrorDictionary;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * 提供 JWT 服务
 *
 * @author nonlinearthink
 */
@Service
@EnableAsync
public class JwtService {
    /**
     * 过期间隔
     */
    public static final int EXPIRE_INTERVAL = 60 * 60 * 24;
    /**
     * 日志记录器
     */
    private final Logger logger = LogManager.getLogger(LogCategory.SYSTEM.getPosition());
    /**
     * 用户私钥
     */
    @Getter
    @Setter
    private static String userPrivateKey;
    /**
     * 管理员私钥
     */
    @Getter
    @Setter
    private static String adminPrivateKey;
    /**
     * redis 键前缀
     */
    private final String prefix = "token_";
    /**
     * Redis 字符串
     */
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public JwtService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 生成 token 字符串
     *
     * @param privateKeyType 私钥类型，根据用户类型划分
     * @return JWT字符串
     */
    public String generateToken(String username, UserType privateKeyType) {
        Calendar calendar = Calendar.getInstance();
        // 签发的时间
        Date issuedAt = calendar.getTime();
        calendar.add(Calendar.SECOND, JwtService.EXPIRE_INTERVAL);
        // 过期的时间
        Date expiration = calendar.getTime();

        Claims jwtClaims = new DefaultClaims()
                // 设置签发者
                .setIssuer(JwtService.class.getName())
                // 设置签发时间
                .setIssuedAt(issuedAt)
                // 设置过期时间
                .setExpiration(expiration)
                // 设置用户
                .setSubject(username);
        String token = Jwts.builder()
                // 设置JWT声明
                .setClaims(jwtClaims)
                // 设置加密算法和私钥
                .signWith(SignatureAlgorithm.HS256, privateKeyType == UserType.ADMIN ? adminPrivateKey : userPrivateKey)
                .compact();
        logger.info("用户" + username + "请求生成密钥 " + token);
        cacheToken(token, username);
        return token;
    }

    /**
     * 检验 token 的合法性
     *
     * @param token JWT字符串
     * @throws BaseException token过期或者签发人不合法
     */
    public String validateToken(String token, UserType privateKeyType) throws BaseException {
        String username;
        try {
            // 解析 token 得到 Jwt 申明信息
            Claims jwtClaims =
                    Jwts.parser().setSigningKey(privateKeyType == UserType.ADMIN ? adminPrivateKey : userPrivateKey).parseClaimsJws(token).getBody();
            username = jwtClaims.getSubject();
            logger.info("解析" + token + "的结果为 " + username);
        } catch (ExpiredJwtException e) {
            throw new BaseException(ErrorDictionary.TOKEN_EXPIRATION, LogCategory.SYSTEM);
        } catch (UnsupportedJwtException e) {
            throw new BaseException(ErrorDictionary.TOKEN_UNSUPPORTED, LogCategory.SYSTEM);
        } catch (MalformedJwtException e) {
            throw new BaseException(ErrorDictionary.TOKEN_MALFORMED, LogCategory.SYSTEM);
        } catch (SignatureException e) {
            throw new BaseException(ErrorDictionary.TOKEN_AUTHORIZATION_FAILED, LogCategory.SYSTEM);
        } catch (IllegalArgumentException e) {
            throw new BaseException(ErrorDictionary.TOKEN_MISSING, LogCategory.SYSTEM);
        }
        // 比较缓存的 token
        if (!token.equals(redisTemplate.opsForValue().get(prefix + username))) {
            throw new BaseException(ErrorDictionary.TOKEN_UNSUPPORTED, LogCategory.SYSTEM);
        }
        return username;
    }

    @Async
    public void cacheToken(String token, String username) {
        // 设置一天的密钥
        redisTemplate.opsForValue().set(prefix + username, token);
        logger.info("设置用户" + username + "的 token 缓存");
    }

    @Async
    public void clearTokenCache(String username) {
        redisTemplate.delete(prefix + username);
        logger.info("清除用户" + username + "的 token 缓存");
    }

}
