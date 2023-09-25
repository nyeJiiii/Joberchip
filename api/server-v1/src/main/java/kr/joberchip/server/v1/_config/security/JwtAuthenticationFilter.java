package kr.joberchip.server.v1._config.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.joberchip.core.user.User;
import kr.joberchip.server.v1._errors.ErrorMessage;
import kr.joberchip.server.v1._errors.exceptions.filter.Exception401;
import kr.joberchip.server.v1._utils.FilterResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String prefixJwt = request.getHeader(JwtProvider.HEADER);

    log.info("prefix : {}", prefixJwt);

    if (prefixJwt == null) {
      chain.doFilter(request, response);
      return;
    }

    log.info("start token verification");

    String jwt = prefixJwt.replace(JwtProvider.TOKEN_PREFIX, "");

    try {
      DecodedJWT decodedJWT = JwtProvider.verify(jwt);

      Long userId = decodedJWT.getClaim("userId").asLong();
      String username = decodedJWT.getClaim("username").asString();
      String userRole = decodedJWT.getClaim("userRoles").asString();

      User user = User.builder().userId(userId).username(username).userRoles(userRole).build();

      CustomUserDetails myUserDetails = new CustomUserDetails(user);
      Authentication authentication =
          new UsernamePasswordAuthenticationToken(
              myUserDetails, myUserDetails.getPassword(), myUserDetails.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(authentication);
      log.info(
          "Security Context에 '{}' 인증 정보를 저장했습니다, Uri: '{}'",
          myUserDetails.getUsername(),
          request.getRequestURI());

      chain.doFilter(request, response);

    } catch (SignatureVerificationException sve) {
      FilterResponse.unAuthorized(response, new Exception401(ErrorMessage.TOKEN_UN_AUTHORIZED));
    } catch (TokenExpiredException tee) {
      FilterResponse.unAuthorized(response, new Exception401(ErrorMessage.TOKEN_EXPIRED));
    } catch (JWTDecodeException exception) {
      FilterResponse.unAuthorized(
          response, new Exception401(ErrorMessage.TOKEN_VERIFICATION_FAILED));
    }
  }
}
