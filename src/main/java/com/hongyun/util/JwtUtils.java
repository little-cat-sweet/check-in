package com.hongyun.util;

import cn.hutool.log.Log;
import com.hongyun.Constants.JwtConstants;
import com.hongyun.entity.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    private final Log log = Log.get();

    @Autowired
    private JwtConstants jwtConstants;

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("username", user.getName());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConstants.getExpiration() * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtConstants.getSecret())
                .compact();
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtConstants.getSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtConstants.getSecret()).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: " + e.getMessage());
        }
        return false;
    }
}
