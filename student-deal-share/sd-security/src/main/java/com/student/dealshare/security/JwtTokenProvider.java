package com.student.dealshare.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * JWT Token 提供者
 */
@Slf4j
@Component
public class JwtTokenProvider {

    /**
     * JWT 密钥
     */
    @Value("${jwt.secret:student-deal-share-secret-key-2024}")
    private String secret;

    /**
     * 过期时间（毫秒）默认 24 小时
     */
    @Value("${jwt.expire:86400000}")
    private Long expire;

    /**
     * 获取密钥
     */
    private Key getSigningKey() {
        byte[] keyBytes = secret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 创建 Token
     *
     * @param userId 用户 ID
     * @return Token
     */
    public String createToken(Long userId) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expire);

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 从 Token 中获取用户 ID
     *
     * @param token Token
     * @return 用户 ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return Long.parseLong(claims.getSubject());
    }

    /**
     * 验证 Token 是否有效
     *
     * @param token Token
     * @return true-有效，false-无效
     */
    public boolean validateToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            log.error("验证 Token 失败：{}", e.getMessage());
            return false;
        }
    }

    /**
     * 获取 Claims
     *
     * @param token Token
     * @return Claims
     */
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取过期时间
     *
     * @return 过期时间（毫秒）
     */
    public Long getExpireTime() {
        return expire;
    }
}
