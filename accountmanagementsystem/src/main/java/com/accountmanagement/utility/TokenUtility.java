package com.accountmanagement.utility;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Service
public class TokenUtility {

    @Value("${authtoken.tokenvalidity.seconds}")
    private Integer tokenValidity;

    @Value("${authtoken.signing.key}")
    private String signingKey;

    @Value("${authtoken.tokenname}")
    private String tokenName;

    @Value("${authtoken.prefix}")
    private String tokenPrefix;

    @Value("${authtoken.issuer}")
    private String claimsIssuer;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(signingKey.getBytes());
    }

    public Boolean isTokenExpired(String jwt) {
        Date expiration = getExpirationDateFromToken(jwt);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String jwt) {
        Date expirationDate = null;
        Jws<Claims> jwsClaims = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(jwt);
        expirationDate = jwsClaims.getPayload().getExpiration();
        return expirationDate;
    }

    public String getJWTSubject(String jwt) {
        Jws<Claims> jwsClaims = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(jwt);
        return jwsClaims.getPayload().getSubject();
    }

    public String generateJwt(String sessionId) {
        String token = Jwts.builder()
                .subject(sessionId)
                .issuer(claimsIssuer)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenValidity * 1000))
                .signWith(getSigningKey())
                .compact();
        return token;
    }

    public String extractSessionId(String accessToken) {
        try {
            if (accessToken == null || accessToken.trim().isEmpty()) {
                throw new BadCredentialsException("Authorization token is required");
            }

            Claims claims = Jwts.parser()
                    .verifyWith((SecretKey) getSigningKey())
                    .requireIssuer(claimsIssuer)
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload();
            String sessionId = claims.getSubject();

            if (sessionId == null || sessionId.trim().isEmpty()) {
                throw new BadCredentialsException("Invalid token session id not found");
            }

            return sessionId;

        } catch (ExpiredJwtException e) {
            throw new CredentialsExpiredException("Token Expired");

        } catch (UnsupportedJwtException e) {
            throw new BadCredentialsException("Token format not supported");

        } catch (MalformedJwtException e) {
            throw new BadCredentialsException("Invalid token format");

        } catch (SignatureException e) {
            throw new BadCredentialsException("Token signature is invalid");

        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Invalid Token");

        } catch (BadCredentialsException | CredentialsExpiredException e) {
            throw e;

        } catch (Exception e) {
            throw new AuthenticationServiceException("Authentication failed due to server error");
        }
    }

}
