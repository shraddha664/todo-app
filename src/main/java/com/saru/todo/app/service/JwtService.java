package com.saru.todo.app.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    private final String SECRET="42264528482B4D6251655468576D5A7134743777217A25432A462D4A404E6352";

    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

   public String extractUsername(String token){
       return extractClaim(token,Claims::getSubject);
   }

    public Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }


    private Boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }


    public String generateToken(String name) {
        Map<String,Object> claims=new HashMap<>();
        return createToken(claims,name);
    }

    private String createToken(Map<String, Object> claims,String name) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(name)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+100*60*60*1000))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username=extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);

    }
}
