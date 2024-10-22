package com.mercadona.demo.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
        if ("admin".equals(username)) {
            return new User("admin", "$2a$12$fKU9Ut0HFH0xjSCgxbX9S.SBPjOxSPAv8t4ppah9WWvBNf2.8pcme"
, new ArrayList<>()); // Contraseña encriptada "password"
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }
}
