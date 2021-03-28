package com.assignments.api.util;

import com.assignments.api.model.User;
import com.assignments.api.security.SecurityConstants;
import com.google.common.hash.Hashing;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;

import static com.assignments.api.security.SecurityConstants.*;

public final class AuthenticationUtils {

    private AuthenticationUtils(){
        //Empty
    }

    private static final int SECOND = 1000;
    private static final int MINUTE = SECOND * 60;
    private static final int HOUR = MINUTE * 60;

    private static final long TOKEN_EXPIRATION_TIME = 15 * MINUTE;

    private static SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
    private static byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SecurityConstants.SECRET);
    private static Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        return salt.toString();
    }

    public static String generateSHA256HashOf(String input) {
        return Hashing.sha256().hashString(input, StandardCharsets.UTF_8).toString();
    }

    public static String generateTokenFor(User user) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(user.getLogin())
                .setIssuedAt(now)
                .setClaims(buildClaimsFor(user))
                .setExpiration(new Date(now.getTime() + TOKEN_EXPIRATION_TIME))
                .signWith(signatureAlgorithm, signingKey).compact();
    }

    private static HashMap<String, Object> buildClaimsFor(User user) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(GROUP_ID_CLAIM, user.getGroupId());
        claims.put(USER_ID_CLAIM, user.getId());
        claims.put(USER_LOGIN_CLAIM, user.getLogin());
        return claims;
    }

    public static User secureUserDataBeforeReturn(User user) {
        user.setPassword("secured");
        user.setSalt("secured");
        return user;
    }

    public static String extractGroupId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.get(GROUP_ID_CLAIM, String.class);
    }
}
