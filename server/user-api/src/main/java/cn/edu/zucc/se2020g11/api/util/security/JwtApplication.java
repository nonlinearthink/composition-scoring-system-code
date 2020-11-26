package cn.edu.zucc.se2020g11.api.util.security;

import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import cn.edu.zucc.se2020g11.api.util.exception.ExceptionDictionary;
import cn.edu.zucc.se2020g11.api.util.log.LogPosition;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * JWT 应用类
 *
 * @author nonlinearthink
 */
public class JwtApplication {

    protected String privateKey;

    public JwtApplication() {
        this.privateKey = RandomStringUtils.random(JwtConfig.PRIVATE_KEY_LENGTH, true, true);
    }

    /**
     * 判断 token 的签发者是否合法
     *
     * @param jwtClaims JWT申明
     * @return 返回 true 如果
     */
    private boolean isValidIssuer(Claims jwtClaims) {
        return jwtClaims.getIssuer().equals(JwtConfig.ISSUER);
    }

    /**
     * 生成 token 字符串
     *
     * @return JWT字符串
     */
    public String generateToken() {
        Calendar calendar = Calendar.getInstance();
        // 签发的时间
        Date issuedAt = calendar.getTime();
        calendar.add(Calendar.SECOND, JwtConfig.EXPIRE_INTERVAL);
        // 过期的时间
        Date expiration = calendar.getTime();

        Claims jwtClaims = new DefaultClaims()
                // 设置签发者
                .setIssuer(JwtConfig.ISSUER)
                // 设置签发时间
                .setIssuedAt(issuedAt)
                // 设置过期时间
                .setExpiration(expiration);

        return Jwts.builder()
                // 设置JWT声明
                .setClaims(jwtClaims)
                // 设置加密算法和私钥
                .signWith(SignatureAlgorithm.HS256, privateKey)
                .compact();
    }

    /**
     * 检验 token 的合法性
     *
     * @param token JWT字符串
     * @throws BaseException token过期或者签发人不合法
     */
    public void validateToken(String token) throws BaseException {
        try {
            // 解析 token 得到JWT申明信息
            Claims jwtClaims = Jwts.parser().setSigningKey(privateKey).parseClaimsJws(token).getBody();
            // 判断签发人是否合法
            if (!isValidIssuer(jwtClaims)) {
                throw new BaseException(ExceptionDictionary.TOKEN_INVALID_ISSUER, LogPosition.SYSTEM);
            }
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

    /**
     * 更新私钥
     */
    public void updatePrivateKey() {
        this.privateKey = RandomStringUtils.random(JwtConfig.PRIVATE_KEY_LENGTH, true, true);
    }
}
