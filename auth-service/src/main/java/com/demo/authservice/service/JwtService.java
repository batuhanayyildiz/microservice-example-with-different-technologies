package com.demo.authservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.key}")
    private String SECRET;
    private final long accessTokenValidity=1000*60*30;


    public  String generateToken(String username){
        Map<String,Object> claims= new HashMap<>();
        return createToken(claims,username);
    }


    public Boolean validateToken(String token, UserDetails userDetails){
        String username= extractValue(token,"username");
        Date expirationDate= extractValue(token,"expiration");
        if( username ==null || expirationDate==null){
            return false;
        }
        return userDetails.getUsername().equals(username) && !expirationDate.before(new Date());



    }
    @SuppressWarnings("unchecked")
    public <T> T extractValue( String token, String value){
        Claims claims= Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        switch (value){
            case "expiration" -> {
                return (T) claims.getExpiration();}
            case "username" -> {return (T) claims.getSubject();
            }
            default -> {
                return null;
            }
        }
    }
    private String createToken( Map<String, Object> claims, String username){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+accessTokenValidity)) // 30 min
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey(){
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
