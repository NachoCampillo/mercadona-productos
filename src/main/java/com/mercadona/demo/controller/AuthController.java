package com.mercadona.demo.controller;

import com.mercadona.demo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public String createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {

        // Autenticación con el nombre de usuario y contraseña
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Nombre de usuario o contraseña incorrectos", e);
        }

        // Cargar los detalles del usuario
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        // Generar el token JWT
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return jwt; // Devolver el token al usuario
    }
}
