package com.example.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

//jwt令牌工具类
public class JwtUtils {
    private static String signKey = "createsignaturealgorithmcreatesignaturealgorithm";
    private static Long expire = 43200000L;

//    生成令牌信息
    public static String generatorJwt(Map<String, Object> claims) {
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, signKey)
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .compact();
        return jwt;
    }

//验证解码令牌信息
    public static Claims parseJwt(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }

}
