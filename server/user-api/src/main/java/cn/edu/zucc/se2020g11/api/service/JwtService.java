package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.config.UserType;
import cn.edu.zucc.se2020g11.api.config.LogPosition;
import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import cn.edu.zucc.se2020g11.api.util.exception.ExceptionDictionary;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * 提供 JWT 服务
 *
 * @author nonlinearthink
 */
@Service
public class JwtService {
    /**
     * 过期间隔
     */
    public static final int EXPIRE_INTERVAL = 60 * 60 * 24;
    /**
     * 日志记录器
     */
    private final Logger logger = LogManager.getLogger(LogPosition.SYSTEM);
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
     * 生成 token 字符串
     *
     * @param privateKeyType 私钥类型，根据用户类型划分
     * @return JWT字符串
     */
    public String generateToken(UserType privateKeyType) {
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
                .setExpiration(expiration);
        return Jwts.builder()
                // 设置JWT声明
                .setClaims(jwtClaims)
                // 设置加密算法和私钥
                .signWith(SignatureAlgorithm.HS256, privateKeyType == UserType.ADMIN ? adminPrivateKey : userPrivateKey)
                .compact();
    }

    /**
     * 检验 token 的合法性
     *
     * @param token JWT字符串
     * @throws BaseException token过期或者签发人不合法
     */
    public String validateToken(String token, UserType privateKeyType) throws BaseException {
        try {
            // 解析 token 得到JWT申明信息
            Claims jwtClaims =
                    Jwts.parser().setSigningKey(privateKeyType == UserType.ADMIN ? adminPrivateKey : userPrivateKey).parseClaimsJws(token).getBody();
            return jwtClaims.getSubject();
        } catch (ExpiredJwtException e) {
            throw new BaseException(ExceptionDictionary.TOKEN_EXPIRATION, LogPosition.SYSTEM);
        } catch (UnsupportedJwtException e) {
            throw new BaseException(ExceptionDictionary.TOKEN_UNSUPPORTED, LogPosition.SYSTEM);
        } catch (MalformedJwtException e) {
            throw new BaseException(ExceptionDictionary.TOKEN_MALFORMED, LogPosition.SYSTEM);
        } catch (SignatureException e) {
            throw new BaseException(ExceptionDictionary.AUTHORIZATION_FAILED, LogPosition.SYSTEM);
        } catch (IllegalArgumentException e) {
            throw new BaseException(ExceptionDictionary.TOKEN_MISSING, LogPosition.SYSTEM);
        }
    }

}
