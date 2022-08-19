package spring.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import spring.constant.ApplicationConstant;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;

public class JWTManager {

    public String signJWS(String username, String password)  {
        Date issued = new Date();
        Date expiration;
        Calendar c = Calendar.getInstance();
        c.setTime(issued);
        c.add(Calendar.DATE, 1);
        expiration = c.getTime();

        SecretKey secretKey = Keys.hmacShaKeyFor((password + ApplicationConstant.SECRET).getBytes());

        return Jwts.builder()
                .setIssuer("bolton")
                .setSubject(username)
                .setIssuedAt(issued)
                .setExpiration(expiration)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> verifyJWS(String jws, String password) {
        SecretKey secretKey = Keys.hmacShaKeyFor((password + ApplicationConstant.SECRET).getBytes());

        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jws);
        } catch (JwtException e) {
            return null;
        }
    }
}
