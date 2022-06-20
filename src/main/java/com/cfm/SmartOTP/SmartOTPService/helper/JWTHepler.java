package com.cfm.SmartOTP.SmartOTPService.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Component
public class JWTHepler {
    public static String createJWT(String secretKey, String issuer, String subject) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //We will sign our JWT with our ApiKey secret in application.yml
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public static String createJWTExp(String secretKey, String id, String issuer, String subject, long ttlMillis,
                                      String timeoutOTP) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //Thời gian JWT và OTP có hiệu lực tính bằng milli giây
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret in application.yml
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .setAudience(timeoutOTP)
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (ttlMillis > 0) {

            //Thời gian JWT và OTP có hết hiệu lực tính bằng milli giây
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    static Claims decodeJWT(String jwt, String secretKey) {

        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    public static Boolean verifyJWT(String subject, String jwt, String jwtSystemKey) {

        Boolean isVerify = false;
        try {

            Claims claims = decodeJWT(jwt, jwtSystemKey);

            if (subject != null && claims.getSubject().equals(subject))

                isVerify = true;

        } catch (Exception e) {

            isVerify = false;
        } finally {

            return isVerify;
        }
    }

    public static String getSecretKeyFromJWT(String jwt, String jwtSystemKey) {

        String secretKey = null;

        try {

            Claims claims = decodeJWT(jwt, jwtSystemKey);

            secretKey = claims.getIssuer();

        } catch (Exception e) {

        } finally {

            return secretKey;
        }
    }

    public static Long getTimeoutFromJWT(String jwt, String jwtSystemKey) {

        Long timeout = 0L;

        try {

            Claims claims = decodeJWT(jwt, jwtSystemKey);

            timeout = Long.parseLong(claims.getAudience());

        } catch (Exception e) {

        } finally {

            return timeout;
        }
    }
}
