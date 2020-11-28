package com.dystopiastudios.easystory.util;

import com.dystopiastudios.easystory.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtCenter {
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    private String token;

    public JwtCenter setToken(String token) {
        this.token = token;
        return this;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    public Boolean validate(UserDetails userDetails) {
        final String username = this.getUsername();
        return (username.equals(userDetails.getUsername()) && !this.isExpired());
    }

    private Claims getAllClaims() {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaim(Function<Claims, T> claimsResolver) {
        final Claims claims = this.getAllClaims();
        return claimsResolver.apply(claims);
    }

    public String getUsername() {
        return this.getClaim(Claims::getSubject);
    }

    public Date getIssuedAtDate() {
        return this.getClaim(Claims::getIssuedAt);
    }

    public Date getExpirationDate() {
        return getClaim(Claims::getExpiration);
    }

    public Boolean isExpired() {
        final Date expiration = this.getExpirationDate();
        return expiration.before(new Date());
    }

    private Boolean ignoreExpiration() {
        return false;
    }

    private Boolean canBeRefreshed() {
        return (!isExpired() || ignoreExpiration());
    }

    private String doGenerateToken(
            Map<String, Object> claims,
            String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 100))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
