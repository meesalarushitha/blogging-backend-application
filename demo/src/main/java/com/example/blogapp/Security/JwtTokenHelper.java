package com.example.blogapp.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenHelper {

  @Value("${jwt.secret}")
  private String secret;

  @Value("{$jwt.expiration")
  private String expiration;

  private SecretKey getSigningKey(){
      byte[] KeyBytes= Decoders.BASE64.decode(secret);
      return Keys.hmacShaKeyFor(KeyBytes);
  }
  public String generateToken(String username){
      return Jwts.builder()
              .setSubject(username)
              .setIssuedAt(new Date())
              .setExpiration(
                      new Date(System.currentTimeMillis() + expiration)
              )
              .signWith(
                      getSigningKey(),
                      SignatureAlgorithm.HS512
              )
              .compact();
  }
    public String getUsernameFromToken(String token) {

        return getClaimFromToken(
                token,
                Claims::getSubject
        );
    }

    public Date getExpirationDate(String token) {

        return getClaimFromToken(
                token,
                Claims::getExpiration
        );
    }

    public <T> T getClaimFromToken(
            String token,
            Function<Claims, T> claimsResolver) {

        Claims claims = getAllClaims(token);

        return claimsResolver.apply(claims);
    }

    private Claims getAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {

        return getExpirationDate(token)
                .before(new Date());
    }

    public boolean validateToken(
            String token,
            UserDetails userDetails) {

        String username =
                getUsernameFromToken(token);

        return username.equals(
                userDetails.getUsername())
                && !isTokenExpired(token);
    }
}

