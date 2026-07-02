package com.cakies.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    // Khóa bí mật (ít nhất 32 ký tự để an toàn)
    private static final String SECRET_KEY = "cakies-secret-key-12345678901234567890";
    // Thời gian sống của token (ví dụ: 1 ngày)
    private static final long EXPIRATION_TIME = 86400000; // 24h

    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // Tạo token từ username
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Lấy username từ token
    public static String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Kiểm tra token có hợp lệ không
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
