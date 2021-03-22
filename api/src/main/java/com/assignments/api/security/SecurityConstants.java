package com.assignments.api.security;

public class SecurityConstants {

    public static final String SECRET = "secret";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";
    public static final String GROUP_ID_CLAIM = "GroupId";
    public static final String USER_ID_CLAIM = "UserId";
    public static final String USER_LOGIN_CLAIM = "UserLogin";
}
