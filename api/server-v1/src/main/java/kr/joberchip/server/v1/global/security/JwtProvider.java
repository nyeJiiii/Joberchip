package kr.joberchip.server.v1.global.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import kr.joberchip.core.user.User;

public class JwtProvider {
  public static final String SECRET = "MySecretKey";
  public static final Long EXP = 1000L * 60 * 60 * 480;
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER = "Authorization";

  public static String create(User user) {
    String jwt =
        JWT.create()
            .withExpiresAt(new Date(System.currentTimeMillis() + EXP))
            .withClaim("userId", user.getUserId())
            .withClaim("username", user.getUsername())
            .withClaim("userRoles", user.getUserRoles())
            .sign(Algorithm.HMAC512(SECRET));
    return TOKEN_PREFIX + jwt;
  }

  public static DecodedJWT verify(String jwt)
      throws SignatureVerificationException, TokenExpiredException {
    return JWT.require(Algorithm.HMAC512(SECRET)).build().verify(jwt);
  }
}
