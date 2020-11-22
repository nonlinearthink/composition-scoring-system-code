package cn.edu.zucc.se2020g11.api.util;

import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import cn.edu.zucc.se2020g11.api.util.exception.ExceptionDictionary;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import reactor.util.annotation.Nullable;

import java.util.Calendar;
import java.util.Date;

/**
 * JWT 支持
 *
 * @author nonlinearthink
 */
public class JwtUtil {
    /**
     * 签发者信息
     */
    private static final String ISSUER = JwtUtil.class.getName();
    /**
     * 过期间隔
     */
    private static final int EXPIRE_INTERVAL = 60 * 60 * 24;
    /**
     * 私钥
     */
    private static final String PRIVATE_KEY = "023bdc63c3c5a4587*9ee6581508b9d03ad39a74fc0c9a9cce604743367c9646b";

    /**
     * 判断 token 过期
     *
     * @param jwtClaims JWT声明
     * @return 返回 true 如果 Token 过期，否则则为 false
     */
    private static boolean isTokenExpired(Claims jwtClaims) {
        return Calendar.getInstance().getTime().compareTo(jwtClaims.getExpiration()) > 0;
    }

    /**
     * 判断 token 的签发者是否合法
     *
     * @param jwtClaims JWT申明
     * @return 返回 true 如果
     */
    private static boolean isValidIssuer(Claims jwtClaims) {
        return jwtClaims.getIssuer().equals(JwtUtil.ISSUER);
    }

    /**
     * 生成 token 字符串
     *
     * @param audience 接收方
     * @param subject  面向的用户
     * @return JWT字符串
     */
    public static String generateToken(String subject, @Nullable String audience) {
        Calendar calendar = Calendar.getInstance();
        // 签发的时间
        Date issuedAt = calendar.getTime();
        calendar.add(Calendar.SECOND, EXPIRE_INTERVAL);
        // 过期的时间
        Date expiration = calendar.getTime();

        Claims jwtClaims = new DefaultClaims()
                // 设置签发者
                .setIssuer(ISSUER)
                // 设置接收方
                .setAudience(audience)
                // 设置面向的用户
                .setSubject(subject)
                // 设置签发时间
                .setIssuedAt(issuedAt)
                // 设置过期时间
                .setExpiration(expiration);

        return Jwts.builder()
                // 设置JWT声明
                .setClaims(jwtClaims)
                // 设置加密算法和私钥
                .signWith(SignatureAlgorithm.HS256, PRIVATE_KEY)
                .compact();
    }

    /**
     * 检验 token 的合法性
     *
     * @param token JWT字符串
     * @return 返回合法token解析出来的用户
     * @throws BaseException token过期或者签发人不合法
     */
    public static String validateToken(String token) throws BaseException {
        // 解析 token 得到JWT申明信息
        Claims jwtClaims = Jwts.parser().setSigningKey(PRIVATE_KEY).parseClaimsJws(token).getBody();
        // 判断是否过期
        if (JwtUtil.isTokenExpired(jwtClaims)) {
            throw new BaseException(ExceptionDictionary.TOKEN_EXPIRATION);
        }
        // 判断签发人是否合法
        if (!JwtUtil.isValidIssuer(jwtClaims)) {
            throw new BaseException(ExceptionDictionary.TOKEN_INVALID_ISSUER);
        }
        return jwtClaims.getSubject();
    }
}
