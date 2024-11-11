package com.example.LunchMatch.Security;

import com.example.LunchMatch.auth.TokenBlackListService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


//This is the service that will generate, validate, decode, and extract info from the token
@Service
public class JwtService {
    //@Value("${application.security.jwt.expiration}")
    private long jwtExpiration = 9000000;
    //@Value("${application.security.jwt.secret-key}")
    private String secretKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    @Autowired
    private TokenBlackListService tokenBlacklistService;

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);


    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }
    public String generateRefreshToken(UserDetails userDetails,Integer userId) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", userId.toString());
        return generateToken(extraClaims, userDetails);
    }
    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return buildToken(claims, userDetails, jwtExpiration);
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long jwrExpiration) {
        var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(issuedAt.getTime() + jwtExpiration);
        logger.info("Token issued at: {}", issuedAt);
        logger.info("Token will expire at: {}", expiration);
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+jwtExpiration))
                .claim("authorities",authorities)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact(); //this method produces the final JWT token


    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token)
                && !tokenBlacklistService.isTokenBlacklisted(token);


    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        logger.info("Token expiration: {}", expiration);
        logger.info("Current time: {}", new Date());
        return extractExpiration(token).before(new Date());//isTokenExpired checks if the expiration date is before the current date:
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);//The apply method of the Function interface is called with the claims object. This applies the claimResolver function to the claims to extract and return the desired claim.
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .setAllowedClockSkewSeconds(9000000)
                .build()

                .parseClaimsJws(token) //It validates the signature and extracts the claims from the token
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
