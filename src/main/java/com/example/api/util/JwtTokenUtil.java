package com.example.api.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {

  private static final Integer ACCESS_TOKEN_EXPIRE = 36000000; //3600000-godzina
  private static final Integer REFRESH_TOKEN_EXPIRE = 648000000;
  private Algorithm algorithm;
  private JWTVerifier verifier;

  @Autowired
  public JwtTokenUtil(@Value("@{jwt.secret}") String secret) {
    this.algorithm = Algorithm.HMAC256(secret.getBytes());
    this.verifier = JWT.require(algorithm).build();
  }

  public DecodedJWT decodeJWT(String token){
    return verifier.verify(token);
  }


  public String createAccessToken(User userPrincipal, String requestUrl){
    String access_token = JWT.create()
        .withSubject(userPrincipal.getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE))
        .withIssuer(requestUrl)
        .withClaim("roles", userPrincipal.getAuthorities()
            .stream().map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList()))
        .sign(algorithm);
    return access_token;
  }

  public String createAccessToken(com.example.api.model.User user, String requestUrl){
    String access_token = JWT.create()
        .withSubject(user.getEmail())
        .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE))
        .withIssuer(requestUrl)
        .withClaim("roles", user.getRoles())
        .sign(algorithm);
    return access_token;
  }

  public String createRefreshToken(String userEmail, String requestUrl){
    String refresh_token = JWT.create()
        .withSubject(userEmail)
        .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE))
        .withIssuer(requestUrl)
        .sign(algorithm);
    return refresh_token;
  }
}
