package com.hotel.bf.config.security.jwt;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import com.hotel.bf.config.security.SecurityUtils;
import com.hotel.bf.config.security.TestUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
@Data
@Component
public class TokenProvider {

    @Value("${spring.security.authentication.jwt.base64-secret}")
    private String secretKey;

    @Value("${spring.security.authentication.jwt.token-validity-in-seconds}")
    private long tokenValidity;

    @Value("${spring.security.authentication.jwt.token-validity-in-seconds-for-remember-me}")
    private long tokenValidityForRememberMe;
    @Value("${spring.security.authentication.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenValidity;

    private static final String AUTHORITIES_KEY = "auth";

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.tokenValidity *= 1000;
        this.tokenValidityForRememberMe *= 1000;
        this.refreshTokenValidity *= 1000;

        var decodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(decodedKey));
    }

    /**
     * Generate a token from user info.
     *
     * @param userDetails
     * @return token
     */
    public String generateJwtToken(final UserDetails userDetails, boolean rememberMe) {
        String authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = System.currentTimeMillis();

        long validityPeriod = rememberMe ? this.tokenValidityForRememberMe : this.tokenValidity;
        Date validity = new Date(System.currentTimeMillis() + validityPeriod);
        return Jwts.builder()
                .claim(AUTHORITIES_KEY, authorities)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }


    public String generateRefreshToken(final UserDetails userDetails) {
        String authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        
        long now = System.currentTimeMillis();
        Date validity = new Date(now + this.refreshTokenValidity);
    
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(now))
                .setExpiration(validity)
                .claim("type", "refresh")
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
            final String username = extractUsername(token);
            
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        
       
    }


    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | SignatureException e) {
            log.error("Token validation failed: {}", e.getMessage());
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
    
        return extractExpiration(token).before(new Date());
    }


     /**
     * Génère à la fois un access token et un refresh token
     *
     * @param userDetails les détails de l'utilisateur
     * @param rememberMe si l'utilisateur veut être rappelé
     * @return Map contenant access_token et refresh_token
     */
    public Map<String, String> generateTokenPair(final UserDetails userDetails, boolean rememberMe) {
        Map<String, String> tokens = new HashMap<>();
        
        // Générer l'access token
        String accessToken = generateJwtToken(userDetails, rememberMe);
        
        // Générer le refresh token
        String refreshToken = generateRefreshToken(userDetails);
        
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        
        return tokens;
    }
}
