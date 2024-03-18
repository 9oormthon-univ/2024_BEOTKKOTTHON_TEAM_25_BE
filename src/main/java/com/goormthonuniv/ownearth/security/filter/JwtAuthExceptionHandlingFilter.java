package com.goormthonuniv.ownearth.security.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormthonuniv.ownearth.common.BaseResponse;
import com.goormthonuniv.ownearth.exception.AuthException;
import com.goormthonuniv.ownearth.exception.GlobalErrorCode;

@Component
public class JwtAuthExceptionHandlingFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    } catch (AuthException e) {
      response.setContentType("application/json; charset=UTF-8");
      response.setStatus(HttpStatus.UNAUTHORIZED.value());

      GlobalErrorCode code = e.getErrorCode();

      BaseResponse<Object> errorResponse = BaseResponse.onFailure(code, null);

      ObjectMapper mapper = new ObjectMapper();
      mapper.writeValue(response.getOutputStream(), errorResponse);
    }
  }
}
