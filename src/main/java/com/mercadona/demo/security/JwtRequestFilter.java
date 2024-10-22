package com.mercadona.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  @Autowired
    private JwtUtil jwtUtil;

  @Autowired
    private UserDetailsService userDetailsService;

  @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
              String username = null;
                  String jwt = null;

                  // Verificar si el token empieza con "Bearer "
                  if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                      jwt = authorizationHeader.substring(7); // Extraer el token sin el prefijo "Bearer "
                      try {
                          username = jwtUtil.extractUsername(jwt); // Extraer el username del token
                      } catch (ExpiredJwtException e) {
                          System.out.println("El token JWT ha expirado");
                      }
                  }

                  // Si el token es válido y no hay autenticación previa
                  if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                      UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                      if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                          // Configurar la autenticación en el contexto de seguridad
                          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                  userDetails, null, userDetails.getAuthorities());
                          authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                      }
                  }
                  chain.doFilter(request, response);
              }
  }