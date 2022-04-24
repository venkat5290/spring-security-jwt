package com.example.security.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Signature;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    @Value("{app.secret}")
    private String secret;

    //Generating a token
    public String generateToken(String subject){

        return Jwts.builder()
                .setSubject(subject)
                .setIssuer("venkat")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+TimeUnit.MINUTES.toMillis(30)))
                .signWith(SignatureAlgorithm.HS512,secret.getBytes())
                .compact();
    }

    //2.Reading Claims
    public Claims getClaims(String token){
        return Jwts.parser().setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    //3.Read exipary date
    public Date getExpiryDate(String token){
        return getClaims(token).getExpiration();
    }

    //4.Get SUBJECT/USERNAME
    public String getUsername(String token){
        return getClaims(token).getSubject();
    }

    //5.Validate Expiry Date
    public boolean isTokenexpiry(String token){
        Date expiryDate = getExpiryDate(token);
        return expiryDate.before(new Date(System.currentTimeMillis()));
    }

    //6.Validating Username
    public boolean validateToken(String token,String username){
        String tokenUserName = getUsername(token);
        return (username.equals(tokenUserName) && !isTokenexpiry(token));
    }
}
