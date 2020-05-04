package com.assignments.api.util;

import com.assignments.api.model.User;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import com.google.common.hash.Hashing;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.xml.bind.DatatypeConverter;
import javax.crypto.spec.SecretKeySpec;

public class AuthenticationUtils {

    private static final int SECOND = 1000;
    private static final int MINUTE = SECOND*60;
    private static final int HOUR = MINUTE*60;

    private static final long TOKEN_EXPIRATION_TIME = 15*MINUTE;
    private static final String SECRET_KEY = "secret";

    private static SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
    private static byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
    private static Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

    public static String generateSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        return salt.toString();
    }

    public static String generateSHA256HashOf(String input){
        return Hashing.sha256().hashString(input, StandardCharsets.UTF_8).toString();
    }

    public static String generateTokenFor(User user){
        Date now = new Date();
        return Jwts.builder()
                .setSubject(user.getLogin())
                .setIssuedAt(now)
                .setClaims(buildClaimsFor(user))
                .setExpiration(new Date(now.getTime() + TOKEN_EXPIRATION_TIME))
                .signWith(signatureAlgorithm, signingKey).compact();
    }

    private static HashMap<String, Object> buildClaimsFor(User user){
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("GroupId", user.getGroupId());
        return claims;
    }

    public static User secureUserDataBeforeReturn(User user){
        user.setPassword("secured");
        user.setSalt("secured");
        return user;
    }
}
