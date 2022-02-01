package com.example.api.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.api.dto.LoginRequest;
import com.example.api.dto.RegisterRequest;
import com.example.api.exception.IllegalProcessingException;
import com.example.api.exception.JwtAuthenticationException;
import com.example.api.exception.TokenNotFoundException;
import com.example.api.service.UserService;
import com.example.api.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@IllegalProcessingException
@RequestMapping("/auth")
@Validated
public class AuthController {

  private final PasswordEncoder passwordEncoder;
  private final UserService userService;
  private final JwtTokenUtil jwtTokenUtil;
  private final AuthenticationManager authenticationManager;

  @Autowired
  public AuthController(PasswordEncoder passwordEncoder, UserService userService, JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager) {
    this.passwordEncoder = passwordEncoder;
    this.userService = userService;
    this.jwtTokenUtil = jwtTokenUtil;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping(value = "/register")
  public void register(@RequestBody @Valid RegisterRequest requestData, HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
    if(userService.existsByEmail(requestData.getEmail()))
      throw new IllegalArgumentException("User with given email already exists");
    com.example.api.model.User user  = new com.example.api.model.User(requestData.getFirstName(),
        requestData.getLastName(), requestData.getEmail(), passwordEncoder.encode(requestData.getPassword()));
    userService.addUser(user);
    attemptLogin(requestData.getEmail(), requestData.getPassword(), request, response);
  }

  @PostMapping("/login")
  public void login(@RequestBody LoginRequest loginData, HttpServletRequest request, HttpServletResponse response) throws IOException {
    attemptLogin(loginData.getEmail(), loginData.getPassword(), request, response);
  }

  @GetMapping("/refresh-token")
  public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      try {
        String refreshToken = authorizationHeader.substring("Bearer ".length());
        DecodedJWT decodedJWT = jwtTokenUtil.decodeJWT(refreshToken);
        String email = decodedJWT.getSubject();
        com.example.api.model.User user = userService.getUserByEmail(email);
        String accessToken = jwtTokenUtil.createAccessToken(user, request.getRequestURL().toString());
        tokenResponse(accessToken, refreshToken, response);
      } catch (Exception e) {
        throw new JwtAuthenticationException("Something was wrong with the token");
      }
    }
    throw new TokenNotFoundException("No token found");
  }

  private void attemptLogin(String email, String password, HttpServletRequest request, HttpServletResponse response) throws IOException {
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(email, password);
    User user = (User) authenticationManager.authenticate(authenticationToken).getPrincipal();
    String accessToken = jwtTokenUtil.createAccessToken(user, request.getRequestURL().toString());
    String refreshToken = jwtTokenUtil.createRefreshToken(user.getUsername(), request.getRequestURL().toString());
    tokenResponse(accessToken, refreshToken, response);
  }

  private void tokenResponse(String accessToken, String refreshToken, HttpServletResponse response) throws IOException {
    Map<String, String> tokens = new HashMap<>();
    tokens.put("access_token", accessToken);
    tokens.put("refresh_token", refreshToken);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    new ObjectMapper().writeValue(response.getOutputStream(), tokens);
  }
}

